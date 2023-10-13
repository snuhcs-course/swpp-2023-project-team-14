package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.HaengshaGrey
import com.example.compose.PlaceholderGrey
import com.example.haengsha.model.dataSource.SignupInfo
import com.example.haengsha.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun exposedDropDown(category: String): String {
    val options =
        when (category) {
            "학과" -> {
                SignupInfo.college
            }

            "학번" -> {
                SignupInfo.studentId
            }

            else -> {
                SignupInfo.interest
            }
        }
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.width(270.dp)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = HaengshaGrey,
                    shape = RoundedCornerShape(10.dp)
                )
                .wrapContentHeight(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .height(60.dp),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                placeholder = {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = category,
                        fontFamily = poppins,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light,
                        color = PlaceholderGrey,
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .height(60.dp),
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    return selectedOptionText
}

@Preview(showBackground = true)
@Composable
fun PreviewExposedDropDown() {
    Box(modifier = Modifier.fillMaxSize() .padding(100.dp)) {
        exposedDropDown("학과")
    }
}