package com.soportereal.invefacon.funciones_de_interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact

@Composable
internal fun BBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar...",
    fontFamily: FontFamily,
    fontWeight: FontWeight = FontWeight.Light,
    backgroundColor: Color = Color.LightGray,
    textColor: Color = Color.DarkGray,
    placeholderColor: Color = Color.Gray,
    iconTint: Color = Color.DarkGray,
    icono: ImageVector = Icons.Filled.Search,
    objetoAdaptardor: FuncionesParaAdaptarContenidoCompact,
    alto: Int,
    ancho:Int,
    trailingIcon : ImageVector = Icons.Filled.Search,
    onTrailingIconClick: (Boolean)->Unit = {},
    mostrarTrailingIcon : Boolean = false
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = fontFamily,
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
                    .width(objetoAdaptardor.ajustarAncho(ancho))
                    .height(objetoAdaptardor.ajustarAltura(alto))
                    .background(backgroundColor, RoundedCornerShape(objetoAdaptardor.ajustarAltura(18)))
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
                                    fontFamily = fontFamily,
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
        }, modifier = Modifier
            .width(objetoAdaptardor.ajustarAncho(ancho))
            .height(objetoAdaptardor.ajustarAltura(alto))
            .then(modifier)
    )
}

