package com.soportereal.invefacon.interfaces.modulos.ventas

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.content.FileProvider
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.events.Event
import com.itextpdf.kernel.events.IEventHandler
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.LineSeparator
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Text
import com.itextpdf.layout.property.AreaBreakType
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.VerticalAlignment
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.obtenerValorParametroEmpresa
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode


private fun convertirNumero(numero: Int): String {
    if (numero == 0) return "cero"

    val unidades = arrayOf(
        "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
        "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    )
    val decenas = arrayOf(
        "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    )
    val centenas = arrayOf(
        "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
        "seiscientos", "setecientos", "ochocientos", "novecientos"
    )

    fun convertirMenorMil(n: Int): String {
        return when {
            n == 0 -> ""
            n == 100 -> "cien"
            n < 20 -> unidades[n]
            n < 100 -> {
                val d = n / 10
                val u = n % 10
                if (u == 0) decenas[d] else "${decenas[d]} y ${unidades[u]}"
            }
            else -> {
                val c = n / 100
                val r = n % 100
                if (r == 0) centenas[c] else "${centenas[c]} ${convertirMenorMil(r)}"
            }
        }
    }

    var n = numero
    val partes = mutableListOf<String>()
    if (n >= 1_000_000) {
        val millones = (n / 1_000_000)
        partes.add(if (millones == 1) "un millón" else "${convertirMenorMil(millones)} millones")
        n %= 1_000_000
    }
    if (n >= 1000) {
        val miles = (n / 1000)
        partes.add(if (miles == 1) "mil" else "${convertirMenorMil(miles)} mil")
        n %= 1000
    }
    if (n > 0) {
        partes.add(convertirMenorMil(n))
    }

    return partes.joinToString(" ")
}

private fun numeroALetrasConMoneda(monto: Double, moneda: String): String {


    val parteEntera = monto.toBigDecimal().setScale(0, RoundingMode.DOWN)
    val centimos = monto.toBigDecimal().subtract(parteEntera).multiply(BigDecimal(100))


    // Elegir textos según moneda
    val (textoMoneda, textoCentimosSingular, textoCentimosPlural) = when (moneda.uppercase()) {
        "USD" -> Triple("DÓLARES", "CENTAVO", "CENTAVOS")
        "EUR" -> Triple("EUROS", "CÉNTIMO", "CÉNTIMOS")
        else -> Triple("COLONES", "CÉNTIMO", "CÉNTIMOS") // CRC por defecto
    }

    val letrasEnteros = convertirNumero(monto.toInt())
    val letrasCentimos = if (centimos > BigDecimal(0)) {
        " CON ${convertirNumero(centimos.toInt())} ${if (centimos == BigDecimal(1)) textoCentimosSingular else textoCentimosPlural}"
    } else {
        ""
    }

    return "${letrasEnteros.trim()} $textoMoneda$letrasCentimos".uppercase()
}

private fun addTextValor(
    label: String,
    valor: String ="",
    fontSize: Float = 8f,
    destacarPrimerTxt: Boolean = true,
    invertirSize : Boolean = false,
    destacarSegundoTxt: Boolean = false,
    align: TextAlignment = TextAlignment.LEFT,
    color: DeviceRgb = DeviceRgb(0,0,0)
): Paragraph {
    val texto = Paragraph()

    if (invertirSize){
        fontSize-1
    }
    val labelText = Text("$label ").setFontSize(fontSize).setFontColor(color)
    if (destacarPrimerTxt) {
        labelText.setBold()
    }
    if (invertirSize){
        fontSize+1
    }else{
        fontSize-1
    }

    val valorText = Text(valor).setFontSize(fontSize).setFontColor(color)
    if (destacarSegundoTxt){
        valorText.setBold()
    }

    texto.add(labelText)
    texto.add(valorText)
    texto.setTextAlignment(align)

    return texto
}

private fun crearCelda(
    contenido: List<Paragraph>,
    colspan: Int,
    bordeIzq: Boolean = false,
    bordeDer: Boolean = false,
    bordeArr: Boolean = false,
    bordeAbj: Boolean = false,
    align: TextAlignment = TextAlignment.LEFT,
    vAlign: VerticalAlignment = VerticalAlignment.MIDDLE,
    margenVertical : Boolean = false
): Cell {
    val cell = Cell(1, colspan)
        .setBorder(Border.NO_BORDER)
        .setTextAlignment(align)
        .setVerticalAlignment(vAlign)

    contenido.forEach {
        cell.add(it).setTextAlignment(align).setVerticalAlignment(vAlign)
    }
    if (margenVertical) cell.setMarginLeft(1.5f).setMarginRight(1.5f)
    if (bordeIzq) cell.setBorderLeft(SolidBorder(1f))
    if (bordeDer) cell.setBorderRight(SolidBorder(1f))
    if (bordeArr) cell.setBorderTop(SolidBorder(1f))
    if (bordeAbj) cell.setBorderBottom(SolidBorder(1f))

    return cell
}

class PageFooterEventHandler(
    private val doc: Document,
    private val miTablaPieDePagina: Table // Solo pasamos la tabla que va en cada pie de página
) : IEventHandler {

    override fun handleEvent(event: Event) {
        val docEvent = event as PdfDocumentEvent
        val page = docEvent.page
        val pageSize = page.pageSize
        val pdfCanvas = PdfCanvas(page)
        val canvas = Canvas(pdfCanvas, page.getDocument(), pageSize)

        val leftMargin = doc.leftMargin
        val rightMargin = doc.rightMargin
        val bottomMargin = doc.bottomMargin // Tu margen inferior reservado

        // ** 1. DIBUJAR NUMERACIÓN DE PÁGINA **
        val pageNum = docEvent.document.getPageNumber(page)
        val footerParagraph = Paragraph("Página #$pageNum").setFontSize(8f).setTextAlignment(TextAlignment.RIGHT)

        // Posicionar el número de página en una posición fija dentro del margen inferior (ej. a 36f del borde)
        canvas.showTextAligned(
            footerParagraph,
            pageSize.width - rightMargin,
            56f, // Posición Y fija para el número de página
            TextAlignment.RIGHT
        )

        // ** 2. DIBUJAR LA TABLA DE PIE DE PÁGINA **
        // La tabla debe estar por encima del número de página y dentro del bottomMargin.
        // Calcula la posición Y para la tabla: (Altura total del margen - Altura de la tabla - pequeño padding)
        val tablaHeight = 28f // Usa la altura estimada fija
        val yPositionForTable = bottomMargin - tablaHeight -40// Un poco de espacio desde el tope del margen

        miTablaPieDePagina.setFixedPosition(
            docEvent.document.getPageNumber(page), // Número de página actual
            leftMargin, // Posición X: Margen izquierdo del documento
            yPositionForTable, // Posición Y: Calculada para que esté dentro del margen inferior
            pageSize.width - leftMargin - rightMargin // Ancho de la tabla
        )
        canvas.add(miTablaPieDePagina)

        canvas.close()
    }
}

fun generarFacturaPDFCompleta(
    context: Context,
    nombreEmpresa: String,
    listaArticulos: List<ArticuloVenta>,
    datosCliente: ClienteVentas,
    detallesDocumento: DetalleDocumentoVentas,
    totales: TotalesVentas,
    listaIvas: SnapshotStateList<ParClaveValor>
) {
    val listaTiposDocumentos by mutableStateOf(
        listOf(
            ParClaveValor("01", "Factura"),
            ParClaveValor("02", "Nota de Débito"),
            ParClaveValor("03", "Nota de Crédito"),
            ParClaveValor("04", "Tiquete"),
            ParClaveValor("09", "Exportación")
        )
    )

    val file = File(context.getExternalFilesDir(null), "Factura_${System.currentTimeMillis()}.pdf")

    val writer = PdfWriter(file)
    val pdf = PdfDocument(writer)
    val doc = Document(pdf)
    /// Define esta tabla fuera de la función generarFacturaPDFCompleta.
    val miTablaPieDePagina: Table = Table(floatArrayOf(1f)).useAllAvailableWidth()
    miTablaPieDePagina.addCell(
        crearCelda(
            listOf(
                addTextValor(obtenerValorParametroEmpresa("189", ""), align = TextAlignment.CENTER)
            ),
            colspan = 1,
            align = TextAlignment.CENTER
        )
    )
    miTablaPieDePagina.addCell(
        crearCelda(
            listOf(
                addTextValor(obtenerValorParametroEmpresa("139", ""), align = TextAlignment.CENTER, destacarPrimerTxt = false)
            ),
            colspan = 1,
            align = TextAlignment.CENTER,
            bordeArr = true
        )
    )
    miTablaPieDePagina.addCell(
        crearCelda(
            listOf(
                addTextValor(obtenerValorParametroEmpresa("137", ""), align = TextAlignment.CENTER, fontSize = 9f)
            ),
            colspan = 1,
            align = TextAlignment.CENTER
        )
    )
// ... cualquier otro contenido para esta tabla
    // Ajusta los márgenes del documento. El '80f' (o más si tu tabla es grande) es el espacio reservado.
    doc.setMargins(12f, 12f, 74f, 12f)

    // Registra el EventHandler, pasándole tu tabla de pie de página
    val footerEventHandler = PageFooterEventHandler(doc, miTablaPieDePagina)
    pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footerEventHandler)
    val tablaEmcabezadoSuperior = Table(floatArrayOf(2f, 1f)) // proporción columnas: 2/3 y 1/3
    tablaEmcabezadoSuperior.useAllAvailableWidth()

    // 👉 Celda izquierda: varios textos (uno debajo del otro)
    val celdaTextoSuperior = Cell()
        .setBorder(Border.NO_BORDER)
        .setVerticalAlignment(VerticalAlignment.MIDDLE) // centra verticalmente
        .setTextAlignment(TextAlignment.LEFT)           // alinea a la izquierda

    // Agregar cada texto como Paragraph
    celdaTextoSuperior.add(
        Paragraph(obtenerValorParametroEmpresa("62", "")).setFontSize(13f).setBold().setTextAlignment(TextAlignment.LEFT)
    )
    celdaTextoSuperior.add(
        addTextValor("Jurídico:", obtenerValorParametroEmpresa("54", ""))
    )
    celdaTextoSuperior.add(
        addTextValor("Cédula:", obtenerValorParametroEmpresa("66", ""))
    )
    celdaTextoSuperior.add(
        addTextValor("Dirección:", obtenerValorParametroEmpresa("130", ""))
    )
    celdaTextoSuperior.add(
        addTextValor("Teléfonos:", obtenerValorParametroEmpresa("73", ""))
    )
    celdaTextoSuperior.add(
        addTextValor("E-Mail:", obtenerValorParametroEmpresa("106", ""))
    )
    celdaTextoSuperior.add(
        Paragraph("${listaTiposDocumentos.find { it.clave == detallesDocumento.tipoDocumento }?.valor?.uppercase()?:"DOC DESCONOCIDO"} ELECTRÓNICA").setFontSize(14f).setBold().setTextAlignment(TextAlignment.LEFT)
    )
    if (detallesDocumento.tipoDocumento in listOf("02", "03")){
        celdaTextoSuperior.add(
            Paragraph("REF: "+detallesDocumento.referencia).setFontSize(12f).setBold().setTextAlignment(TextAlignment.LEFT)
        )
    }

    tablaEmcabezadoSuperior.addCell(celdaTextoSuperior)
    val rutaImagen = obtenerParametroLocal(context, clave = "$nombreEmpresa.jpg")
    val fileImg = File(rutaImagen)
    if (fileImg.exists()){
        val logoBitmap : Bitmap = BitmapFactory.decodeFile(rutaImagen)
        logoBitmap.let {
            // 👉 Celda derecha: imagen
            val stream = ByteArrayOutputStream()
            logoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageData = ImageDataFactory.create(stream.toByteArray())
            val image = Image(imageData)
                .setAutoScale(true)
                .scaleToFit(140f, 140f)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)

            val celdaLogo = Cell()
                .add(image)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)

            tablaEmcabezadoSuperior.addCell(celdaLogo)
        }
    }

    // Agregar tabla al documento
    doc.add(tablaEmcabezadoSuperior)
    // DATOS FACTURA

    val tablaDatosFactura = Table(floatArrayOf(1f, 1f,1f, 1f,1f, 1f)).useAllAvailableWidth()

    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(Paragraph()
                .add(Text("Consecutivo: ").setFontSize(8f))
                .add(Text(detallesDocumento.numero).setFontSize(10f).setBold())
                .setTextAlignment(TextAlignment.LEFT)) ,
            colspan = 3, bordeIzq = true, bordeArr = true
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(
                addTextValor(
                    label = "Clave:",
                    detallesDocumento.clave,
                    destacarPrimerTxt = false
                )
            ),
            colspan = 3, bordeDer = true, bordeArr = true
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(
                addTextValor(
                    "Fecha emisión:",
                    detallesDocumento.fecha,
                    destacarPrimerTxt = false
                )
            ),
            colspan = 3, bordeIzq = true
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(
                addTextValor(
                    "Fecha vencimiento:",
                    detallesDocumento.fecha,
                    destacarPrimerTxt = false
                )
            ),
            colspan = 3, bordeDer = true
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(
                Paragraph()
                    .add(Text("Condición venta: ").setFontSize(8f))
                    .add(Text(detallesDocumento.formapagocodigo).setFontSize(10f).setBold())
                    .setTextAlignment(TextAlignment.LEFT)
            ),
            colspan = 2, bordeIzq = true, bordeAbj = true
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            contenido = listOf(
                addTextValor(
                    label = "Días crédito:",
                    detallesDocumento.diasCredito,
                    destacarPrimerTxt = false,
                    align = TextAlignment.CENTER
                )
            ),
            colspan = 2, bordeAbj = true, align = TextAlignment.CENTER
        )
    )
    tablaDatosFactura.addCell(
        crearCelda(
            listOf(
                addTextValor("Agente:",
                    detallesDocumento.agentecodigo,
                    destacarPrimerTxt = false,
                    align = TextAlignment.RIGHT
                )
            ),
            colspan = 2, bordeDer = true, bordeAbj = true, align = TextAlignment.RIGHT
        )
    )
    tablaDatosFactura.setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER)
    doc.add(tablaDatosFactura)

    val tablaDatosCliente = Table(2).useAllAvailableWidth()
    val tablaClienteQr = Table(floatArrayOf(3f, 1f)).useAllAvailableWidth()
    tablaDatosCliente.addCell(
        crearCelda(
            listOf(
                addTextValor(
                    datosCliente.nombre,"", destacarPrimerTxt = false, fontSize = 10f
                ),
                addTextValor(
                    "TELEFONO:",datosCliente.telefonos, destacarPrimerTxt = false, invertirSize = true
                )
                ,
                addTextValor(
                    "DIRECCION:",datosCliente.direccion, destacarPrimerTxt = false, invertirSize = true
                )
                ,
                addTextValor(
                    "E-MAIL:",datosCliente.email, destacarPrimerTxt = false, invertirSize = true
                )
            ), bordeAbj = true,
            bordeArr = true,
            bordeIzq = true,
            colspan = 1,
            align = TextAlignment.LEFT
        )
    )
    tablaDatosCliente.addCell(
        crearCelda(
            listOf(
                addTextValor(
                    "CUENTA:",datosCliente.id_cliente, destacarPrimerTxt = false, invertirSize = true
                ),
                addTextValor(
                    "CEDULA:",datosCliente.cedula, destacarPrimerTxt = false, invertirSize = true
                )
                ,
                addTextValor(
                    "USUARIO:",detallesDocumento.usuariocodigo, destacarPrimerTxt = false, invertirSize = true
                )
            ), bordeAbj = true,
            bordeArr = true,
            bordeDer = true,
            colspan = 1,
            align = TextAlignment.RIGHT,
            vAlign = VerticalAlignment.BOTTOM
        )
    )

    // 👉 Tabla contenedora que tendrá a la izquierda la imagen y a la derecha la tabla de da

    // ----- Columna derecha: tabla de datos cliente -----
    val celdaTablaCliente = Cell()
        .add(tablaDatosCliente) // aquí agregamos la tabla interna ya construida
        .setBorder(Border.NO_BORDER)
        .setVerticalAlignment(VerticalAlignment.MIDDLE)
        .setTextAlignment(TextAlignment.LEFT)

    tablaClienteQr.addCell(celdaTablaCliente)

// ----- Columna izquierda: imagen (QR) -----
    val drawable = context.getDrawable(R.drawable.qrsrl)
    if (drawable != null) {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imageData = ImageDataFactory.create(stream.toByteArray())

        val image = Image(imageData)
            .setAutoScale(true)
            .scaleToFit(35f, 35f)
            .setHorizontalAlignment(HorizontalAlignment.CENTER)

        val celdaImagen = Cell()
            .add(image)
            .setBorder(Border.NO_BORDER)
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
            .setTextAlignment(TextAlignment.CENTER)

        tablaClienteQr.addCell(celdaImagen)
    } else {
        // Si no hay imagen, agrega una celda vacía
        tablaClienteQr.addCell(
            Cell().setBorder(Border.NO_BORDER)
        )
    }

// 👉 Agregar la tabla contenedora al documento
    doc.add(Paragraph("")) // espacio opcional
    doc.add(tablaClienteQr)

    val tablaDatosSecundarios = Table(3).useAllAvailableWidth()
    tablaDatosSecundarios.addCell(
        crearCelda(
            listOf(
                addTextValor(
                    "PEDIDO POR:",
                    detallesDocumento.pedido,
                    destacarPrimerTxt = false,
                    invertirSize = true,
                    destacarSegundoTxt = true
                )
            ), bordeAbj = true,
            bordeArr = true,
            bordeIzq = true,
            colspan = 1,
            align = TextAlignment.LEFT
        )
    )
    tablaDatosSecundarios.addCell(
        crearCelda(
            listOf(
                addTextValor(
                    "ENTREGA:",
                    detallesDocumento.entrega,
                    destacarPrimerTxt = false,
                    invertirSize = true,
                    destacarSegundoTxt = true,
                    align = TextAlignment.CENTER
                )
            ), bordeAbj = true,
            bordeArr = true,
            colspan = 1,
            align = TextAlignment.CENTER
        )
    )

    tablaDatosSecundarios.addCell(
        crearCelda(
            listOf(
                addTextValor(
                    "ORDEN COMPRA:",
                    detallesDocumento.ordenCompra,
                    destacarPrimerTxt = false,
                    invertirSize = true,
                    destacarSegundoTxt = true,
                    align = TextAlignment.RIGHT
                )
            ), bordeAbj = true,
            bordeArr = true,
            bordeDer = true,
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )
    doc.add(Paragraph(""))
    doc.add(tablaDatosSecundarios)
    doc.add(Paragraph(""))

    // Definir 11 columnas reales, sin usar colspan:
    val columnasArticulos = floatArrayOf(
        1f,  // Cant
        2f,  // Código
        2f,  // Nombre
        1f, // Precio/U
        1f,  // Desc
        2f,  // Gravado
        1f,  // Exonerado
        1f,// Exento
        1f,  // IVA
        2f,  // Monto IVA
        3f   // Total
    )

    var tablaArticulos = Table(columnasArticulos).useAllAvailableWidth()

    // Encabezado sin colspan
    fun celdaEncabezado(titulo: String, align: TextAlignment = TextAlignment.CENTER): Cell {
        return Cell().add(
            Paragraph(titulo).setFontSize(8f).setTextAlignment(align)
        ).setBorder(Border.NO_BORDER)
            .setBorderBottom(SolidBorder(1f))
            .setBorderTop(SolidBorder(1f))
    }

    // Función para crear el encabezado de la tabla de artículos (la usaremos varias veces)
    fun createArticulosHeaderCells() {
        tablaArticulos.addCell(celdaEncabezado("Cant", TextAlignment.LEFT).setBorderLeft(SolidBorder(1f)))
        tablaArticulos.addCell(celdaEncabezado("Código", TextAlignment.LEFT))
        tablaArticulos.addCell(celdaEncabezado("Nombre", TextAlignment.LEFT))
        tablaArticulos.addCell(celdaEncabezado("Precio/U", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Desc", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Gravado", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Exonerado", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Exento", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("IVA", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Monto IVA", TextAlignment.RIGHT))
        tablaArticulos.addCell(celdaEncabezado("Total", TextAlignment.RIGHT).setBorderRight(SolidBorder(1f)))
    }
    createArticulosHeaderCells()

    var cantidadLienasArticulosEnPagina = 0 // Contador para las filas de *artículos* en la página actual (sin contar el encabezado)
    var cantidadMaximaArticulosPagina = 14 // Inicia con 14 artículos para la primera página
    val lineasTotales = 5 // Las 5 líneas que ocupan los totales


// Bucle para añadir artículos
    listaArticulos.forEachIndexed { index, art ->

        // Lógica para determinar si necesitamos un salto de página ANTES de añadir el artículo actual
        val isLastItem = (listaArticulos.size - 1 == index)
        val spaceNeededForNextArticle = 1 // Cada artículo ocupa 1 línea
        val totalLinesCurrentlyOnPage = cantidadLienasArticulosEnPagina // Solo artículos
        val remainingSpaceOnPage = cantidadMaximaArticulosPagina - totalLinesCurrentlyOnPage

        // Si es el último artículo Y no hay espacio para los totales en esta página,
        // O si ya llenamos la cantidad máxima de artículos para esta página.
        if ((isLastItem && remainingSpaceOnPage < lineasTotales + spaceNeededForNextArticle) || (totalLinesCurrentlyOnPage >= cantidadMaximaArticulosPagina)) {
            // Añade la tabla actual al documento antes de forzar el salto de página
            doc.add(tablaArticulos)
            doc.add(Paragraph("")) // Espacio si es necesario, o elimínalo si no lo quieres

            // Forzar un salto de página
            doc.add(AreaBreak(AreaBreakType.NEXT_PAGE))

            // Reiniciar la tabla para la nueva página y añadir su encabezado
            tablaArticulos = Table(columnasArticulos).useAllAvailableWidth()
            createArticulosHeaderCells() // Añade el encabezado a la nueva tabla

            cantidadLienasArticulosEnPagina = 0 // Reiniciar el contador de artículos para la nueva página
            cantidadMaximaArticulosPagina = 22 // Para las páginas siguientes, soporta 22 artículos
        }

        // Añade el artículo a la tabla actual
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(art.ArticuloCantidad.toString(), destacarPrimerTxt = false, align = TextAlignment.LEFT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(art.Descripcion + " Cabys: " + art.cabys, align = TextAlignment.LEFT, fontSize = 8.2f)), 10))
        for (i in 0 until 3){
            if (i == 1){
                tablaArticulos.addCell(crearCelda(listOf(addTextValor(art.Cod_Articulo, destacarPrimerTxt = false, align = TextAlignment.LEFT)), 1))
            }else{
                tablaArticulos.addCell(crearCelda(listOf(addTextValor("", destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
            }
        }
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(art.ArticuloVenta), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(art.ArticuloDescuentoPorcentage), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(art.ArticuloVentaGravado), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(montoString = art.ArticuloVentaExonerado, isString = true), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(montoString = art.ArticuloVentaExento, isString = true), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(art.ArticuloIvaPorcentage.toInt().toString(), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(art.ArticuloIvaMonto), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))
        tablaArticulos.addCell(crearCelda(listOf(addTextValor(separacionDeMiles(art.ArticuloVentaTotal), destacarPrimerTxt = false, align = TextAlignment.RIGHT)), 1))

        cantidadLienasArticulosEnPagina++
    }

    if (tablaArticulos.numberOfRows > 1) { // 1 porque ya tiene el encabezado
        doc.add(tablaArticulos)
    }

    val linea = LineSeparator(SolidLine())
    linea.setMarginTop(1f)
    linea.setMarginBottom(1f)
    doc.add(linea)

    val tablaTotales = Table(floatArrayOf(7f, 7f,1f, 1f, 1f, 1f)).useAllAvailableWidth().setTextAlignment(TextAlignment.RIGHT)
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor("ITEMS: "+listaArticulos.size, destacarPrimerTxt = false, align = TextAlignment.RIGHT)),
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor("TIPO CAMBIO: "+totales.tipoCambio, destacarPrimerTxt = false, align = TextAlignment.RIGHT)),
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )
    var listaTemp = mutableListOf<Paragraph>()
    listaTemp.addAll(listOf(
        addTextValor("SUB TOTAL", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor("SERV. GRAVADO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor("SERV. EXONERADO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor("SERV. EXENTO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor("IMP.SERV.REST.", destacarPrimerTxt = false, align = TextAlignment.RIGHT)
    ))
    listaIvas.forEach {
        listaTemp.add(addTextValor("IVA (${it.clave}%)", destacarPrimerTxt = false, align = TextAlignment.RIGHT))
    }
    tablaTotales.addCell(
        crearCelda(
            listaTemp,
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )

    listaTemp = mutableListOf()
    listaTemp.addAll(listOf(
        addTextValor(separacionDeMiles(totales.TotalMercGravado+totales.TotalServGravado), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor(separacionDeMiles(totales.TotalServGravado), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor(separacionDeMiles(totales.TotalServExonerado), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor(separacionDeMiles(totales.TotalServExento), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
        addTextValor(separacionDeMiles(totales.totalImpuestoServi), align = TextAlignment.RIGHT)
    ))
    listaIvas.forEach {
        listaTemp.add(addTextValor(separacionDeMiles(montoString = it.valor, isString = true), destacarPrimerTxt = false, align = TextAlignment.RIGHT))
    }

    tablaTotales.addCell(
        crearCelda(
            listaTemp,
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )

    tablaTotales.addCell(
        crearCelda(
            listOf(
                addTextValor("DESCUENTO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor("MERC. GRAVADO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor("MERC. EXONERADO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor("MERC. EXENTO", destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor("TOTAL IVA", align = TextAlignment.RIGHT),
                addTextValor("IVA DEVUELTO", align = TextAlignment.RIGHT)
            ),
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )

    tablaTotales.addCell(
        crearCelda(
            listOf(
                addTextValor(separacionDeMiles(totales.TotalDescuento), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor(separacionDeMiles(totales.TotalMercGravado), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor(separacionDeMiles(totales.TotalMercExonerado), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor(separacionDeMiles(totales.TotalMercExento), destacarPrimerTxt = false, align = TextAlignment.RIGHT),
                addTextValor(separacionDeMiles(totales.totaliva), align = TextAlignment.RIGHT),
                addTextValor(separacionDeMiles(totales.TotalIvaDevuelto), align = TextAlignment.RIGHT)
            ),
            colspan = 1,
            align = TextAlignment.RIGHT
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor("COPIA", align = TextAlignment.LEFT, fontSize = 14f)),
            colspan = 4,
            align = TextAlignment.LEFT
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor("TOTAL", align = TextAlignment.LEFT, fontSize = 13f)),
            colspan = 1,
            align = TextAlignment.LEFT,
            bordeArr = true
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor(detallesDocumento.monedacodigo+ " "+ separacionDeMiles(totales.Total), align = TextAlignment.RIGHT, fontSize = 13f)),
            colspan = 1,
            align = TextAlignment.RIGHT,
            bordeArr = true
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(addTextValor(numeroALetrasConMoneda(totales.Total, moneda = detallesDocumento.monedacodigo), destacarPrimerTxt = false ,align = TextAlignment.RIGHT, fontSize = 7f)),
            colspan = 6,
            align = TextAlignment.RIGHT
        )
    )
    tablaTotales.addCell(
        crearCelda(
            listOf(
                addTextValor(detallesDocumento.detalle, align = TextAlignment.LEFT, fontSize = 9f),
                addTextValor(obtenerValorParametroEmpresa("140", ""), align = TextAlignment.LEFT, destacarPrimerTxt = false),
                addTextValor(obtenerValorParametroEmpresa("141", ""), align = TextAlignment.LEFT, destacarPrimerTxt = false, color = DeviceRgb(80, 80, 80), fontSize = 7f)
            ),
            colspan = 6,
            align = TextAlignment.LEFT
        )
    )
    doc.add(tablaTotales)
    doc.add(Paragraph(""))
    doc.add(Paragraph(""))
    doc.add(Paragraph(""))
    doc.add(Paragraph(""))
    val tablaFirmaCuentas = Table(floatArrayOf(1f, 2f)).useAllAvailableWidth()
    tablaFirmaCuentas.addCell(
        crearCelda(
            listOf(
                addTextValor("", align = TextAlignment.CENTER)
            ),
            colspan = 1
        )
    )
    tablaFirmaCuentas.addCell(
        crearCelda(
            listOf(
                addTextValor("CUENTAS DE BANCO PARA TRANSFERENCIAS:", align = TextAlignment.LEFT),
                addTextValor(obtenerValorParametroEmpresa("133", ""), align = TextAlignment.LEFT, destacarPrimerTxt = false),
                addTextValor(obtenerValorParametroEmpresa("134", ""), align = TextAlignment.LEFT, destacarPrimerTxt = false),
                addTextValor(obtenerValorParametroEmpresa("135", ""), align = TextAlignment.LEFT, destacarPrimerTxt = false)
            ),
            align = TextAlignment.LEFT,
            colspan = 2,
            bordeArr = true,
            bordeDer = true,
            bordeAbj = true,
            bordeIzq = true
        )
    )
    tablaFirmaCuentas.addCell(
        crearCelda(
            listOf(
                addTextValor("Recibido /Firma y Cédula", align = TextAlignment.CENTER)
            ),
            colspan = 1,
            bordeArr = true,
            margenVertical = true,
            vAlign = VerticalAlignment.TOP,
            align = TextAlignment.CENTER
        )
    )
    tablaFirmaCuentas.addCell(
        crearCelda(
            listOf(
                addTextValor("", align = TextAlignment.CENTER)
            ),
            colspan = 2
        )
    )
    doc.add(tablaFirmaCuentas)

    doc.close()

    // COMPARTIR PDF
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Compartir factura PDF"))
}



