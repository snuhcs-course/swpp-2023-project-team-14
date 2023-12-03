package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.model.dataSource.SignupInfo
import com.example.haengsha.ui.screens.login.signup.convertToEnglish
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.HaengshaTheme
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.md_theme_light_background
import com.example.haengsha.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDown(category: String): String {
    val options =
        if (category == "학과") {
            SignupInfo.college
        } else {
            SignupInfo.studentId
        }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf("") }

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
                        fontSize = 16.sp,
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
                modifier = Modifier.background(
                    color = md_theme_light_background,
                ),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .height(60.dp)
                            .padding(horizontal = 5.dp),
                        text = {
                            Text(
                                text = selectionOption,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    )
                    if (options.indexOf(selectionOption) != options.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .background(Color(0xFFE0E0E0))
                        )
                    }
                }
            }
        }
    }
    return selectedOptionText
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun multiSelectDropDown(category: String): List<String> {
    val options = SignupInfo.interest
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf(listOf("")) }
    var textFieldText by rememberSaveable { mutableStateOf("") }
    var convertedList by rememberSaveable { mutableStateOf(listOf("")) }

    selectedOptionText = selectedOptionText.sorted()
    textFieldText =
        if (selectedOptionText.size == 1) {
            selectedOptionText[0]
        } else {
            selectedOptionText.joinToString(", ").drop(2)
        }

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
                value = textFieldText,
                onValueChange = { },
                placeholder = {
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = category,
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = PlaceholderGrey,
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                singleLine = true,
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )
            )
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(
                        color = md_theme_light_background,
                    ),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    var isSelected by rememberSaveable {
                        mutableStateOf(
                            selectedOptionText.contains(
                                selectionOption
                            )
                        )
                    }

                    DropdownMenuItem(
                        modifier = Modifier
                            .height(60.dp)
                            .padding(horizontal = 10.dp)
                            .background(color = Color.Transparent),
                        text = {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectionOption,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                                CheckBox(
                                    color = if (isSelected) ButtonBlue else Color.Transparent,
                                    size = 20
                                )
                            }
                        },
                        onClick = {
                            if (selectedOptionText.contains(selectionOption)) {
                                isSelected = false
                                selectedOptionText =
                                    selectedOptionText.filter { it != selectionOption }
                                convertedList =
                                    convertedList.filter { it != convertToEnglish(selectionOption) }
                            } else {
                                isSelected = true
                                selectedOptionText += selectionOption
                                convertedList += convertToEnglish(selectionOption)
                            }
                        }
                    )
                    if (options.indexOf(selectionOption) != options.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .background(Color(0xFFE0E0E0))
                        )
                    }
                }
            }
        }
    }
    return convertedList
}

@Preview(showBackground = true)
@Composable
fun PreviewExposedDropDown() {
    HaengshaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            dropDown("학과")
        }
    }
}