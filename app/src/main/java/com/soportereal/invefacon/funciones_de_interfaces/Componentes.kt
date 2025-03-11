package com.soportereal.invefacon.funciones_de_interfaces

import android.text.BoringLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.soportereal.invefacon.R
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.obtenerEstiloBody
import com.soportereal.invefacon.interfaces.obtenerEstiloTitle

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
    objetoAdaptardor: FuncionesParaAdaptarContenidoCompact,
    alto: Int = 0,
    ancho:Int = 0,
    trailingIcon : ImageVector = Icons.Filled.Search,
    onTrailingIconClick: (Boolean)->Unit = {},
    mostrarTrailingIcon : Boolean = false,
    utilizarMedidas : Boolean = true
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.akshar_medium)),
            fontWeight = fontWeight,
            color = textColor,
            textAlign = TextAlign.Justify
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .wrapContentSize()
                    .background(backgroundColor, RoundedCornerShape(objetoAdaptardor.ajustarAltura(10)))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
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
                        Icon(
                            imageVector = icono,
                            contentDescription = "Icono Buscar",
                            tint = iconTint,
                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    fontFamily = FontFamily(Font(R.font.akshar_medium)),
                                    fontWeight = fontWeight,
                                    color = placeholderColor,
                                    maxLines = 1
                                )
                            }
                            innerTextField()
                        }
                    }
                    if (mostrarTrailingIcon){
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = "Trailing Icon",
                            tint = iconTint,
                            modifier = Modifier
                                .size(objetoAdaptardor.ajustarAltura(30))
                                .clickable { onTrailingIconClick(true) }
                        )
                    }
                }
            }
        },
        modifier = Modifier
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

@Composable
internal fun BButton(
    modifier: Modifier,
    backgroundColor: Color =  Color(0xFF244BC0),
    contenteColor: Color = Color.White,
    maxLines: Int = 1,
    textSize: TextUnit = obtenerEstiloBody(),
    valorRetornar : Boolean = true,
    onClick : (Boolean) -> Unit,
    text : String,
    conSombra : Boolean = true
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
        }, contentPadding = PaddingValues(2.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text,
                fontFamily = FontFamily(Font(R.font.akshar_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = textSize,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
internal fun TText(
    text: String,
    modifier: Modifier,
    fontSize: TextUnit = obtenerEstiloTitle(),
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    fontWeight: FontWeight = FontWeight.SemiBold
){
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.akshar_medium)),
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        maxLines = maxLines,
        textAlign = textAlign,
        modifier = Modifier.then(modifier),
        overflow = TextOverflow.Ellipsis
    )
}

