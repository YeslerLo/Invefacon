package com.soportereal.invefacon.funciones_de_interfaces

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.soportereal.invefacon.R
import com.soportereal.invefacon.interfaces.inicio_sesion.ocultarTeclado
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
internal fun TText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = obtenerEstiloBodySmall(),
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    fontFamily: FontFamily = FontFamily(Font(R.font.akshar_medium)),
    fontWeight: FontWeight? = FontWeight.SemiBold
){
    Text(
        text = text,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign,
        modifier = Modifier.then(modifier),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
internal fun BBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar...",
    fontWeight: FontWeight = FontWeight.Light,
    backgroundColor: Color = Color.LightGray,
    textColor: Color = Color.DarkGray,
    placeholderColor: Color = Color.Gray,
    iconTint: Color = Color.DarkGray,
    icono: ImageVector = Icons.Filled.Search,
    objetoAdaptardor: FuncionesParaAdaptarContenido,
    alto: Int = 0,
    ancho:Int = 0,
    trailingIcon : ImageVector = Icons.Filled.Search,
    onTrailingIconClick: (Boolean)->Unit = {},
    mostrarTrailingIcon : Boolean = false,
    utilizarMedidas : Boolean = true,
    fontSize: TextUnit = obtenerEstiloLabelBig(),
    cantidadLineas: Int = 1,
    mostrarLeadingIcon: Boolean = true,
    textAlign: TextAlign = TextAlign.Justify,
    darFormatoMiles: Boolean = false,
    soloPermitirValoresNumericos : Boolean = false,
    mostrarClave: Boolean = false,
    opciones: List<ParClaveValor> = emptyList(),
    enable: Boolean = true,
    onFocus : () -> Unit = {},
    offFocus : () -> Unit = {},
    iconsSize : Int = 30
) {
    var tieneFoco by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val contexto = LocalContext.current
    val iconoDdmOpcionesFlechasLaterales = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center
    ){
        Column {
            BasicTextField(
                value =
                try{
                    if (darFormatoMiles) {
                        if (tieneFoco) {
                            value
                        } else {
                            if (value.isNotEmpty()) {
                                String.format(Locale.US, "%,.2f", value.replace(",", "").toDouble())
                            } else ""
                        }
                    }else value
                } catch (e: Exception) {
                    mostrarMensajeError("ERROR EN DATO INGRESADO:: ${e.message}")
                    value
                },
                onValueChange = onValueChange,
                maxLines = cantidadLineas,
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.akshar_medium)),
                    fontWeight = fontWeight,
                    color = textColor,
                    textAlign = textAlign,
                    fontSize = fontSize
                ),
                enabled = if (opciones.isNotEmpty()) false else enable,
                keyboardOptions = if (soloPermitirValoresNumericos) {
                    KeyboardOptions(keyboardType = KeyboardType.Number) // Solo permite números
                } else {
                    KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        ocultarTeclado(contexto)
                        focusManager.clearFocus()
                    }
                ),
                singleLine = cantidadLineas ==1,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = modifier
                            .wrapContentSize()
                            .background(backgroundColor, RoundedCornerShape(objetoAdaptardor.ajustarAltura(10)))
                            .padding(horizontal = objetoAdaptardor.ajustarAncho(8), vertical = objetoAdaptardor.ajustarAltura(4)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                if (mostrarLeadingIcon){
                                    Icon(
                                        imageVector = icono,
                                        contentDescription = "Icono Buscar",
                                        tint = iconTint,
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(iconsSize))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Box(modifier = Modifier.weight(1f)) {
                                    if (value.isEmpty()) {
                                        Text(
                                            text = placeholder,
                                            fontFamily = FontFamily(Font(R.font.akshar_medium)),
                                            fontWeight = fontWeight,
                                            color = placeholderColor,
                                            maxLines = 1,
                                            fontSize = fontSize,
                                            textAlign = textAlign,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                            if (mostrarTrailingIcon || opciones.isNotEmpty()){
                                if (opciones.isEmpty()){
                                    Icon(
                                        imageVector = trailingIcon,
                                        contentDescription = "Trailing Icon",
                                        tint = iconTint,
                                        modifier = Modifier
                                            .size(objetoAdaptardor.ajustarAltura(iconsSize))
                                            .clickable { onTrailingIconClick(true) }
                                    )
                                }else{
                                    IconButton(
                                        onClick = {expanded = !expanded},
                                        modifier = Modifier
                                            .size(objetoAdaptardor.ajustarAltura(iconsSize))
                                            .padding(0.dp)
                                    ) {
                                        Icon(
                                            imageVector = iconoDdmOpcionesFlechasLaterales,
                                            contentDescription = "Icono flechas",
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .size(objetoAdaptardor.ajustarAltura(iconsSize))
                                                .padding(0.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .onFocusChanged { estadoFoco ->
                        tieneFoco = estadoFoco.isFocused // Detectar el estado de foco
                        if (tieneFoco){
                            onFocus()
                        }else{
                            offFocus()
                        }
                    }
                    .let {
                        if (opciones.isNotEmpty()) {
                            it.clickable { expanded = !expanded }
                        } else {
                            it
                        }
                    }
                    .then(
                        if (utilizarMedidas){
                            Modifier.width(objetoAdaptardor.ajustarAncho(ancho))
                                .height(objetoAdaptardor.ajustarAltura(alto))
                        }else{
                            Modifier
                        }
                    )
                    .then(modifier)
            )
        }
    }

    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .heightIn(max = objetoAdaptardor.ajustarAltura(400))
                .widthIn(
                    max = objetoAdaptardor.ajustarAncho(350),
                    min = objetoAdaptardor.ajustarAncho(100)
                )
        ) {
            val ultimo = opciones.size-1
            opciones.forEachIndexed { index, opcion ->
                var isOpcionVisible by remember { mutableStateOf(false) }
                LaunchedEffect(isOpcionVisible) {
                    delay(index * 15L) // Retrasa cada artículo en 100ms según su índice
                    isOpcionVisible = true
                }

                AnimatedVisibility(
                    visible = isOpcionVisible,
                    enter = fadeIn(animationSpec = tween(15)) + slideInVertically(initialOffsetY = { it })
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.clickable {
                            onValueChange(opcion.clave)
                            expanded = false
                        }
                    ) {
                        Row {
                            Checkbox(
                                checked = value == opcion.valor,
                                onCheckedChange = { valor ->
                                    onValueChange(opcion.clave)
                                    expanded = false
                                },
                                modifier = Modifier
                                    .scale(0.7f),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF244BC0),
                                    uncheckedColor = Color.Gray,
                                    checkmarkColor = Color.White
                                )
                            )
                            BButton(
                                text = if (mostrarClave) opcion.clave else opcion.valor,
                                onClick = {
                                    onValueChange(opcion.clave)
                                    expanded = false
                                },
                                contenteColor = Color.Black,
                                backgroundColor = Color(0xFFFFFFFF),
                                conSombra = false,
                                textSize = obtenerEstiloTitleSmall(),
                                objetoAdaptardor = objetoAdaptardor
                            )
                        }
                        if(ultimo != index) HorizontalDivider(thickness = 1.dp, color = Color.Black)
                    }
                }
            }
        }
    }

//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .background(Color.White)
//                .heightIn(max = objetoAdaptardor.ajustarAltura(700))
//                .widthIn(
//                    max = objetoAdaptardor.ajustarAncho(350),
//                    min = objetoAdaptardor.ajustarAncho(100)
//                )
//        ) {
//            opciones.forEach { contenido->
//                DropdownMenuItem(
//                    onClick = {
//                        onValueChange(contenido.clave)
//                        expanded = false // Cierra el menú después de seleccionar
//                    },
//                    text = {
//                        Text(
//                            if (mostrarClave) contenido.clave else contenido.valor,
//                            fontFamily = FontFamily(Font(R.font.akshar_medium)),
//                            fontWeight = FontWeight.Medium,
//                            fontSize = fontSize,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            textAlign = TextAlign.Center,
//                            color = Color.Black
//                        )
//                    },
//                    modifier = Modifier.background(Color.White)
//                )
//
//            }
//        }

}

@Composable
internal fun BButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color =  Color(0xFF244BC0),
    contenteColor: Color = Color.White,
    maxLines: Int = 1,
    textSize: TextUnit = obtenerEstiloBodySmall(),
    valorRetornar : Boolean = true,
    onClick : (Boolean) -> Unit,
    text : String,
    conSombra : Boolean = true,
    objetoAdaptardor: FuncionesParaAdaptarContenido,
    mostrarIcono : Boolean = false,
    icono: ImageVector = Icons.Default.AddCircle,
    textAlign: TextAlign = TextAlign.Center
){
    Button(
        modifier = Modifier.then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contenteColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contenteColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (conSombra) 5.dp else 0.dp),
        onClick = {
            onClick(valorRetornar)
        }, contentPadding =  PaddingValues(start = objetoAdaptardor.ajustarAncho(6), end = objetoAdaptardor.ajustarAncho(6), top = 0.dp, bottom = 0.dp),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(10)),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ){
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4))){

                if (mostrarIcono){
                    Icon(
                        imageVector = icono,
                        contentDescription = "",
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(20)).padding(0.dp),
                        tint = contenteColor
                    )
                }

                Text(
                    text,
                    fontFamily = FontFamily(Font(R.font.akshar_medium)),
                    fontWeight = FontWeight.Light,
                    fontSize = textSize,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = textAlign
                )
            }
        }

    }
}



@Composable
internal fun TextFieldMultifuncional(
    label: String = "",
    valor: String,
    nuevoValor: (String)-> Unit = {},
    nuevoValor2: (ParClaveValor)-> Unit = {},
    isUltimo: Boolean? = false,
    modoEdicionActivado: Boolean= true,
    contieneOpciones: Boolean = false,
    opciones: SnapshotStateMap<String, String> = mutableStateMapOf("1" to "1"),
    usarOpciones2: Boolean = false,
    usarOpciones4: Boolean = false,
    opciones4: List<ParClaveValor> = emptyList(),
    @SuppressLint("MutableCollectionMutableState") opciones2: MutableState<LinkedHashMap<String, String>> = mutableStateOf(LinkedHashMap()),
    isSeleccionarFecha: Boolean = false,
    darFormatoMiles: Boolean = false,
    soloPermitirValoresNumericos: Boolean = false,
    cantidadLineas: Int = 20,
    textPlaceholder: String= "",
    permitirPuntosDedimales: Boolean = false,
    permitirComas: Boolean= false,
    tomarAnchoMaximo: Boolean = true,
    medidaAncho: Int = 0,
    mostrarClave: Boolean = false,
    usarOpciones3 : Boolean = false,
    opciones3 : List<String> = listOf(""),
    mostrarLeadingIcon : Boolean = false,
    leadingIcon : ImageVector = Icons.Default.Search,
    leadingIconColor: Color = Color.DarkGray,
    usarModifierForSize: Boolean = false,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = obtenerEstiloBodyBig(),
    mostrarPlaceholder: Boolean = true,
    mostrarLabel: Boolean = true,
    usarFormatoFecha2: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    mostrarTrailingIcon: Boolean = true,
    onFocus : () -> Unit = {}
){
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expanded by remember { mutableStateOf(false) }
    val iconoDdmOpcionesFlechasLaterales = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    val scrollState= rememberScrollState(0)
    val solicitadorFoco = remember { FocusRequester() }
    var tieneFoco by remember { mutableStateOf(false) }
    // Estados para manejar la fecha seleccionada

    // Fecha actual para inicializar el DatePicker
    val contexto = LocalContext.current
    val focusManager = LocalFocusManager.current
    val calendario = Calendar.getInstance()
    val anioActual = calendario.get(Calendar.YEAR)
    val mesActual = calendario.get(Calendar.MONTH)
    val diaActual = calendario.get(Calendar.DAY_OF_MONTH)

    val onFechaSeleccionada: (Int, Int, Int) -> Unit = { anio, mes, dia ->
        // Obtenemos la fecha actual
        val calendarioActual = Calendar.getInstance()
        val fechaActual = calendarioActual.timeInMillis

        // Creamos una instancia de Calendar para la fecha seleccionada
        val calendarioSeleccionado = Calendar.getInstance().apply {
            set(anio, mes, dia, 0, 0, 0)  // Establecemos el año, mes y día seleccionados
            set(Calendar.MILLISECOND, 0)
        }
        val fechaSeleccionada = calendarioSeleccionado.timeInMillis

        // Validamos que la fecha seleccionada no sea mayor a la fecha actual
        if (fechaSeleccionada > fechaActual) {
            estadoRespuestaApi.cambiarEstadoRespuestaApi(
                mostrarRespuesta = true,
                datosRespuesta = JSONObject("""{"code":400,"status":"error","data":"La fecha seleccionada no puede ser mayor a la de hoy"}""")
            )
            expanded = false
        } else {
            if (usarFormatoFecha2){
                val fechaFormateada = String.format(
                    Locale.ROOT,
                    "%04d-%02d-%02d",
                    anio, mes + 1, dia
                )
                nuevoValor(fechaFormateada)
                expanded = false
            }else{
                val fechaFormateada = String.format(
                    Locale.ROOT,
                    "%04d-%02d-%02d 00:00:00.000",
                    anio, mes + 1, dia
                )
                nuevoValor(fechaFormateada)
                expanded = false
            }

        }
    }

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TextField(
            enabled = if (contieneOpciones) false else modoEdicionActivado,
            value =
                try {
                    if (darFormatoMiles) {
                        if (tieneFoco) {
                            valor // Mostrar el valor sin formato mientras el campo tiene el foco
                        } else {
                            if (valor.isNotEmpty()) {
                                String.format(Locale.US, "%,.2f", valor.replace(",", "").toDouble())
                            } else ""
                        }
                    }else valor
                } catch (e: Exception) {
                    mostrarMensajeError("ERROR EN DATO INGRESADO: ${e.message}")
                    valor
                }
            ,
            onValueChange =  {
                if (soloPermitirValoresNumericos && permitirPuntosDedimales){
                    // Permitir solo caracteres numéricos y punto decimal
                    nuevoValor(it.replace(",", "").replace("-", "")) // Actualizar sin comas
                }
                else if (soloPermitirValoresNumericos && permitirComas){
                    nuevoValor(it.replace(".", "").replace("-", "")) // Actualizar sin comas
                }
                else if (soloPermitirValoresNumericos){
                    nuevoValor(it.replace(".", "").replace(",", "").replace("-", ""))
                }
                else{
                    nuevoValor(it)
                }
            },
            textStyle = TextStyle(
                fontFamily = fontAksharPrincipal,
                fontWeight =    FontWeight.Light,
                fontSize =  fontSize,
                color = Color.Black,
                textAlign = textAlign
            ),
            singleLine = cantidadLineas == 1,
            label = if(mostrarLabel){
                {
                    Text(
                        label,
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = fontSize ,
                        color = Color.DarkGray,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        maxLines = cantidadLineas
                    )
                }
            } else {null},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = if (isUltimo == true)Color.White else Color.Black,
                focusedIndicatorColor = if (isUltimo == true)Color.White else Color.Black,
                cursorColor = Color(0xFF244BC0),
                disabledContainerColor = Color.White,
                disabledIndicatorColor = if(isUltimo == true)Color.White else Color.Black

            ),
            modifier = Modifier
                .let {
                    if (contieneOpciones && modoEdicionActivado) {
                        it.clickable { expanded = !expanded }
                    } else {
                        it
                    }
                }
                .then(
                    if (!usarModifierForSize) {
                        if (tomarAnchoMaximo) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier.width(objetoAdaptardor.ajustarAncho(medidaAncho))
                        }
                    } else {
                        modifier
                    }
                )
                .focusRequester(solicitadorFoco)
                .onFocusChanged { estadoFoco ->
                    tieneFoco = estadoFoco.isFocused // Detectar el estado de foco
                    if (tieneFoco){
                        onFocus()
                    }
                },
            placeholder = if(mostrarPlaceholder) {
                {
                    Text(
                        textPlaceholder,
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize =  fontSize,
                        color = Color.DarkGray,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = textAlign,
                        maxLines = cantidadLineas,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }else{
                null
            }
            ,
            trailingIcon = if (contieneOpciones && modoEdicionActivado && !isSeleccionarFecha && mostrarTrailingIcon){
                    {
                        IconButton(onClick = {expanded = !expanded}) {
                            Icon(
                                imageVector = iconoDdmOpcionesFlechasLaterales,
                                contentDescription = "Icono flechas",
                                tint = Color.Black
                            )
                        }
                    }
                }else{
                    null
                },
            keyboardOptions = if (soloPermitirValoresNumericos) {
                KeyboardOptions(keyboardType = KeyboardType.Number) // Solo permite números
            } else {
                KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ) // Permite cualquier tipo de entrada
            },
            keyboardActions = KeyboardActions(
                onAny = {
                    focusManager.clearFocus()
                    ocultarTeclado(contexto)
                }
            ),
            maxLines = cantidadLineas,
            leadingIcon =  if (mostrarLeadingIcon) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = "Icono",
                        tint = leadingIconColor
                    )
                }
            } else {
                null // No se muestra ningún icono
            }
        )


        AnimatedVisibility(
            visible = expanded && !isSeleccionarFecha,
            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
        ){
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .heightIn(max = objetoAdaptardor.ajustarAltura(700))
                    .widthIn(max =objetoAdaptardor.ajustarAncho(350), min = objetoAdaptardor.ajustarAncho(100)),
                scrollState = scrollState
            ) {
                if (usarOpciones2){
                    opciones2.value.entries.forEach { (clave, contenido) ->
                        DropdownMenuItem(
                            onClick = {
                                nuevoValor (clave)
                                expanded = false // Cierra el menú después de seleccionar
                            },
                            text = {
                                Text(
                                    if (mostrarClave) clave else contenido,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloLabelBig(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }
                else if(usarOpciones3){
                    opciones3.forEach { contenido->
                        DropdownMenuItem(
                            onClick = {
                                nuevoValor (contenido)
                                expanded = false // Cierra el menú después de seleccionar
                            },
                            text = {
                                Text(
                                    contenido,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = fontSize,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }
                else if(usarOpciones4){
                    opciones4.forEach { contenido->
                        DropdownMenuItem(
                            onClick = {
                                nuevoValor2 (contenido)
                                expanded = false // Cierra el menú después de seleccionar
                            },
                            text = {
                                Text(
                                    if(mostrarClave) contenido.clave else contenido.valor,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = fontSize,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }
                else{
                    opciones.forEach { (clave, contenido) ->
                        DropdownMenuItem(
                            onClick = {
                                nuevoValor (clave)
                                expanded = false // Cierra el menú después de seleccionar
                            },
                            text = {
                                Text(
                                    if (mostrarClave) clave else contenido,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = fontSize,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier.background(Color.White)
                        )
                    }
                }

            }
        }

        if (expanded && isSeleccionarFecha){
            DatePickerDialog(
                contexto,
                { _, anio, mes, dia ->
                    onFechaSeleccionada(anio, mes, dia)
                },
                anioActual,
                mesActual,
                diaActual
            ).show()
        }
    }
}

@Composable
fun MenuConfirmacion(
    titulo: String,
    subTitulo: String,
    onAceptar: ()-> Unit,
    onDenegar : ()-> Unit,
    txBtAceptar : String = "Salir",
    txBtDenegar : String = "Cancelar",
    mostrarMenu: Boolean
){
    // Mostrar el diálogo si es necesario
    if (mostrarMenu) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { onDenegar() }, // Cerrar el diálogo sin acción
            title = {
                Text(
                    titulo,
                    fontFamily = FontFamily(Font(R.font.akshar_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = obtenerEstiloTitleMedium(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            text = {
                Text(
                    subTitulo,
                    fontFamily = FontFamily(Font(R.font.akshar_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = obtenerEstiloBodyBig(),
                    overflow = TextOverflow.Ellipsis
                )
            },
            confirmButton = {
                Button(
                    onClick = {onDenegar()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White // Color del contenido (texto e iconos)
                    ),
                     contentPadding =  PaddingValues(6.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {
                    Text(
                        txBtDenegar,
                        fontFamily = FontFamily(Font(R.font.akshar_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = obtenerEstiloBodyBig(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White // Color del contenido (texto e iconos)
                    ),
                    onClick = {
                        onAceptar()
                    },
                    contentPadding =  PaddingValues(6.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {
                    Text(
                        txBtAceptar,
                        fontFamily = FontFamily(Font(R.font.akshar_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = obtenerEstiloBodyBig(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}

@Composable
fun ButtonFecha(
    modifier: Modifier = Modifier,
    backgroundColor: Color =  Color(0xFF244BC0),
    contenteColor: Color = Color.White,
    maxLines: Int = 1,
    textSize: TextUnit = obtenerEstiloBodySmall(),
    nuevoValor : (String) -> Unit,
    valor : String,
    conSombra : Boolean = true,
    objetoAdaptardor: FuncionesParaAdaptarContenido
) {

    var mostrarCalendario by remember { mutableStateOf(false) }
    // Fecha actual para inicializar el DatePicker
    val contexto = LocalContext.current
    val focusManager = LocalFocusManager.current
    val calendario = Calendar.getInstance()
    val anioActual = calendario.get(Calendar.YEAR)
    val mesActual = calendario.get(Calendar.MONTH)
    val diaActual = calendario.get(Calendar.DAY_OF_MONTH)

    val onFechaSeleccionada: (Int, Int, Int) -> Unit = { anio, mes, dia ->
        // Obtenemos la fecha actual
        val calendarioActual = Calendar.getInstance()
        val fechaActual = calendarioActual.timeInMillis

        // Creamos una instancia de Calendar para la fecha seleccionada
        val calendarioSeleccionado = Calendar.getInstance().apply {
            set(anio, mes, dia, 0, 0, 0)  // Establecemos el año, mes y día seleccionados
            set(Calendar.MILLISECOND, 0)
        }
        val fechaSeleccionada = calendarioSeleccionado.timeInMillis

        // Validamos que la fecha seleccionada no sea mayor a la fecha actual
        if (fechaSeleccionada > fechaActual) {
            mostrarMensajeError("La fecha seleccionada no puede ser mayor a la de hoy")
            mostrarCalendario = false
        } else {
            val fechaFormateada = String.format(
                Locale.ROOT,
                "%04d-%02d-%02d",
                anio, mes + 1, dia
            )
            nuevoValor(fechaFormateada)
            mostrarCalendario = false
        }
    }
    
    fun formatearFecha(fechaStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Formato de entrada
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("es", "ES")) // Formato de salida en español

        val date: Date = inputFormat.parse(fechaStr)!! // Convertimos el string a Date
        return outputFormat.format(date) // Convertimos Date a string en el formato deseado
    }

    Button(
        modifier = Modifier.then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contenteColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contenteColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (conSombra) 5.dp else 0.dp),
        onClick = {
            DatePickerDialog(
                contexto,
                { _, anio, mes, dia ->
                    onFechaSeleccionada(anio, mes, dia)
                },
                anioActual,
                mesActual,
                diaActual
            ).show()
            focusManager.clearFocus()
        },
        contentPadding = PaddingValues(
            start = objetoAdaptardor.ajustarAncho(6),
            end = objetoAdaptardor.ajustarAncho(6),
            top = 0.dp,
            bottom = 0.dp
        ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(10)),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                formatearFecha(valor),
                fontFamily = FontFamily(Font(R.font.akshar_medium)),
                fontWeight = FontWeight.Light,
                fontSize = textSize,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }

}

data class ParClaveValor(
    val clave : String = "",
    var valor: String = "",
    var tipo: String = "", // ALMACENA EL TIPO PRECIO DE VENTA
    var venta : String = "", // MODULO FACTURACION EN EL CALCULO DE IVAS
    val existencia : Double = 0.00, // Para Bodegas modulo Facturacion
    val descripcion : String = "" // Descripcion de parametro empresa
)
