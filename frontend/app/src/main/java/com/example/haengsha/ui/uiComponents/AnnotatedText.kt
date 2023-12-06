package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.poppins

@Composable
fun OrganizerSignupInstructionText() {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = deviceWidth / 10),
        text = buildAnnotatedString {
            append("단체 유저 가입을 원하신다면 ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("haengsha@gmail.com") }
            append("으로 아래 사항을 준수하여 이메일로 보내주세요. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("형식") }
            append("\n제목 앞에 [행샤 단체 인증]을 기재해주세요. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("내용") }
            append("\n1. 단체명 \n2. 단체장 이름 \n3. 단체를 증명할 수 있는 서류, 사진(pdf, jpg, png) \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("상기 내용을 이메일에 반드시 포함해주세요.") }
            append("\n\n단체 증빙에는 평균 2~3일이 소요되며, 인증 절차 종료 후 이메일 회신을 통해 결과를 알려드립니다.")

        },
        fontFamily = poppins,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun OrganizerFindPasswordInstructionText() {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = deviceWidth / 10),
        text = buildAnnotatedString {
            append("단체 계정의 비밀번호를 찾고 싶으시다면 ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("haengsha@gmail.com") }
            append("으로 아래 사항을 준수하여 이메일로 보내주세요. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("형식") }
            append("\n제목 앞에 [행샤 단체 계정 비밀번호 찾기]을 기재해주세요. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("내용") }
            append("\n1. 단체명 \n2. 단체장 이름 \n3. 단체를 증명할 수 있는 서류, 사진(pdf, jpg, png) \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("상기 내용을 이메일에 반드시 포함해주세요.") }
            append("\n\n단체 증빙에는 평균 2~3일이 소요되며, 인증 절차 종료 후 이메일 회신을 통해 결과를 알려드립니다.")

        },
        fontFamily = poppins,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    )
}


@Composable
fun TermsOfUseScreenText() {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1. 목적") }
            append("\n본 약관은 \'행샤\'(이하 \"조직\")에서 제공하는 커뮤니티 서비스(이하 \"서비스\")의 이용과 관련하여 조직과 이용자의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("2.용어의 정의") }
            append("\n본 약관에서 사용하는 용어의 정의는 다음과 같습니다. \n\n\"이용자\": 조직의 서비스에 접속하여 본 약관에 따라 조직이 제공하는 서비스를 받는 회원 및 비회원을 말합니다. \n\"회원\": 조직과 이용 계약을 체결하고 이용자 아이디를 부여받아 서비스를 이용하는 이용자를 말합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("3.약관의 게시 및 변경") }
            append("\n조직은 본 약관의 내용을 이용자가 쉽게 알 수 있도록 서비스 초기 화면에 게시합니다. \n조직은 약관의 규제에 관한 법률, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 관련 법령을 위배하지 않는 범위에서 본 약관을 변경할 수 있습니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("4.서비스의 제공 및 변경") }
            append("\n조직은 다음과 같은 서비스를 제공합니다. \n\n커뮤니티 서비스\n기타 조직이 추가적으로 개발하거나, 다른 조직과의 협력계약 등을 통해 회원에게 제공하는 일체의 서비스\n조직은 필요한 경우 서비스의 내용을 변경할 수 있으며, 이 경우 변경된 서비스의 내용 및 제공일자를 명시하여 현행 서비스 내용의 공지사항 또는 서비스 초기 화면을 통해 게시합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("5.서비스 이용계약의 성립") }
            append("\n서비스 이용계약은 이용자의 이용신청에 대한 조직의 이용승낙으로 성립합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("6.이용자의 의무") }
            append("\n이용자는 본 약관에서 규정하는 사항과 서비스 이용안내, 조직이 통지하는 사항을 준수하여야 하며, 기타 조직의 업무에 방해되는 행위를 하여서는 안 됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("7.서비스 이용제한 및 중지") }
            append("\n조직은 이용자가 본 약관의 의무를 위반하거나 서비스의 정상적인 운영을 방해한 경우, 경고, 일시 정지, 영구 이용정지 등으로 서비스 이용을 단계적으로 제한할 수 있습니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("8.면책 조항") }
            append("\n조직은 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임이 면제됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("9.분쟁해결") }
            append("\n본 약관에 관한 분쟁은 대한민국의 법률을 준거법으로 하며, 분쟁의 해결은 민사소송법상의 관할법원에서 진행됩니다.")
        },
        fontFamily = poppins,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun TermsOfUseModalText() {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1. 목적") }
            append("\n본 약관은 \'행샤\'(이하 \"조직\")에서 제공하는 커뮤니티 서비스(이하 \"서비스\")의 이용과 관련하여 조직과 이용자의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("2.용어의 정의") }
            append("\n본 약관에서 사용하는 용어의 정의는 다음과 같습니다. \n\n\"이용자\": 조직의 서비스에 접속하여 본 약관에 따라 조직이 제공하는 서비스를 받는 회원 및 비회원을 말합니다. \n\"회원\": 조직과 이용 계약을 체결하고 이용자 아이디를 부여받아 서비스를 이용하는 이용자를 말합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("3.약관의 게시 및 변경") }
            append("\n조직은 본 약관의 내용을 이용자가 쉽게 알 수 있도록 서비스 초기 화면에 게시합니다. \n조직은 약관의 규제에 관한 법률, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 관련 법령을 위배하지 않는 범위에서 본 약관을 변경할 수 있습니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("4.서비스의 제공 및 변경") }
            append("\n조직은 다음과 같은 서비스를 제공합니다. \n\n커뮤니티 서비스\n기타 조직이 추가적으로 개발하거나, 다른 조직과의 협력계약 등을 통해 회원에게 제공하는 일체의 서비스\n조직은 필요한 경우 서비스의 내용을 변경할 수 있으며, 이 경우 변경된 서비스의 내용 및 제공일자를 명시하여 현행 서비스 내용의 공지사항 또는 서비스 초기 화면을 통해 게시합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("5.서비스 이용계약의 성립") }
            append("\n서비스 이용계약은 이용자의 이용신청에 대한 조직의 이용승낙으로 성립합니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("6.이용자의 의무") }
            append("\n이용자는 본 약관에서 규정하는 사항과 서비스 이용안내, 조직이 통지하는 사항을 준수하여야 하며, 기타 조직의 업무에 방해되는 행위를 하여서는 안 됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("7.서비스 이용제한 및 중지") }
            append("\n조직은 이용자가 본 약관의 의무를 위반하거나 서비스의 정상적인 운영을 방해한 경우, 경고, 일시 정지, 영구 이용정지 등으로 서비스 이용을 단계적으로 제한할 수 있습니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("8.면책 조항") }
            append("\n조직은 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임이 면제됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("9.분쟁해결") }
            append("\n본 약관에 관한 분쟁은 대한민국의 법률을 준거법으로 하며, 분쟁의 해결은 민사소송법상의 관할법원에서 진행됩니다.")
        },
        modifier = Modifier.testTag("TermsOfUseModal"),
        fontFamily = poppins,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PrivacyPolicyScreenText() {
    Text(
        text = buildAnnotatedString {
            append("본 개인정보 수집 및 처리 방침은 \'행샤\'(이하 \"조직\")에서 운영하는 커뮤니티 서비스(이하 \"서비스\")에 적용됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1. 개인정보의 수집 항목 및 방법") }
            append("\n\n조직은 회원가입, 서비스 제공 및 상담 등을 위해 아래와 같은 개인정보를 수집하고 있습니다. \n\n필수 항목: 이메일 주소, 비밀번호, 닉네임, 학과, 학번 \n선택 항목: 관심사 \n개인정보 수집 방법: 회원가입, 서비스 이용 과정에서 사용자로부터 직접 수집 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("2. 개인정보의 수집 및 이용 목적") }
            append("\n\n서비스 제공, 이용자 식별 및 본인 확인, 불량 회원의 부정 이용 방지, 서비스 이용에 대한 통계, 고객 서비스 등 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("3. 개인정보의 보유 및 이용 기간") }
            append("\n\n조직은 원칙적으로 개인정보 수집 및 이용 목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 단, 관계 법령의 규정에 의하여 보존할 필요가 있는 경우 조직은 아래와 같이 일정 기간 동안 회원 정보를 보관합니다. \n\n계약 또는 청약철회 등에 관한 기록: 5년 \n소비자의 불만 또는 분쟁처리에 관한 기록: 3년 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("4. 개인정보의 파기 절차 및 방법") }
            append("\n\n조직은 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 파기 절차 및 방법은 다음과 같습니다. \n\n파기 절차: 이용자가 회원가입 등을 위해 입력한 정보는 목적이 달성된 후 별도의 DB에 옮겨져 내부 방침 및 기타 관련 법령에 따라 일정 기간 저장된 후 혹은 즉시 파기됩니다. \n파기 방법: 전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.")
        },
        fontFamily = poppins,
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun PrivacyPolicyModalText() {
    Text(
        text = buildAnnotatedString {
            append("본 개인정보 수집 및 처리 방침은 \'행샤\'(이하 \"조직\")에서 운영하는 커뮤니티 서비스(이하 \"서비스\")에 적용됩니다. \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("1. 개인정보의 수집 항목 및 방법") }
            append("\n\n조직은 회원가입, 서비스 제공 및 상담 등을 위해 아래와 같은 개인정보를 수집하고 있습니다. \n\n필수 항목: 이메일 주소, 비밀번호, 닉네임, 학과, 학번 \n선택 항목: 관심사 \n개인정보 수집 방법: 회원가입, 서비스 이용 과정에서 사용자로부터 직접 수집 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("2. 개인정보의 수집 및 이용 목적") }
            append("\n\n서비스 제공, 이용자 식별 및 본인 확인, 불량 회원의 부정 이용 방지, 서비스 이용에 대한 통계, 고객 서비스 등 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("3. 개인정보의 보유 및 이용 기간") }
            append("\n\n조직은 원칙적으로 개인정보 수집 및 이용 목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 단, 관계 법령의 규정에 의하여 보존할 필요가 있는 경우 조직은 아래와 같이 일정 기간 동안 회원 정보를 보관합니다. \n\n계약 또는 청약철회 등에 관한 기록: 5년 \n소비자의 불만 또는 분쟁처리에 관한 기록: 3년 \n\n")

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("4. 개인정보의 파기 절차 및 방법") }
            append("\n\n조직은 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 파기 절차 및 방법은 다음과 같습니다. \n\n파기 절차: 이용자가 회원가입 등을 위해 입력한 정보는 목적이 달성된 후 별도의 DB에 옮겨져 내부 방침 및 기타 관련 법령에 따라 일정 기간 저장된 후 혹은 즉시 파기됩니다. \n파기 방법: 전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.")
        },
        modifier = Modifier.testTag("PrivacyPolicyModal"),
        fontFamily = poppins,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
}