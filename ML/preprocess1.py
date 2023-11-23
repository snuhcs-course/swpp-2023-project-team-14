import numpy as np 
import pandas as pd
from typing import List 

def replace_special_characters(df: pd.DataFrame) -> pd.DataFrame:
    assert '내용' in df.columns, "Column '내용' not found in dataframe"
    df['내용'] = df['내용'].str.replace('\n', ' ').str.replace('\r', ' ').str.replace('\t', ' ')
    
    return df 

def process_events() -> pd.DataFrame:
    academic_df = pd.read_csv('acad.csv')
    academic_df = replace_special_characters(academic_df)
    festival_df = pd.read_csv('festival.csv')
    festival_df = replace_special_characters(festival_df)
    academic_df['축제 여부'] = 0
    festival_df['축제 여부'] = 1
    event_df = pd.concat([academic_df, festival_df], axis=0).reset_index(drop=True)
    event_df['combined'] = event_df.apply(lambda row: f"{row['행사명']}, {row['내용']}", axis=1)
    event_df.to_csv('combined_events.csv', index=True)

    return event_df 

def generate_event_map() -> dict:
    event_dict = {}
    event_dict['수리과학부 강연 (주제: 작용소대수와 양자정보이론)'] = 5
    event_dict['뉴욕한인금융인협회(KFS) - 학부생을 위한 career mentoring seminar'] = 30
    event_dict['2023 진로특강 시리즈_졸업하면 뭐할래? (미대 진로 특강)'] = 46
    event_dict["‘과학, 생명, 예술(Science, Life, Art)’ 예비 포럼"] = 49
    event_dict['삼성 파운드리의 현재와 미래'] = 11
    event_dict['Drawing with machines'] = 47
    event_dict['Transparency of AI: The Best Practices of Dealing with Social Bots'] = 44
    event_dict['의료데이터 활용을 위한 연구검색 솔루션'] = 12
    event_dict['수학기반산업데이터해석연구센터 콜로키엄'] = 1
    event_dict['동북아시아센터 워크숍'] = 9

    event_dict['중앙도서관 별빛 영화제'] = 66
    event_dict['SNU 필하모닉 오케스트라 정기연주회 : 라흐마니노프의 저녁'] = 82
    event_dict['2023 인문X사회X자전 연합축제: You in 샤'] = 69
    event_dict['2023 자연대 가을 축제 <You, Science?>'] = 86
    event_dict['2023 서울대학교 의류학과 패션쇼 ‘서울, SEOUL’'] = 98
    event_dict['응생화 X 생과부 일일호프'] = 51
    event_dict['혼성 댄스 동아리 혼또니 정기공연'] = 67
    event_dict['네비게이토 선교회 신입생 재학생 초청 모임'] = 93
    event_dict['ECU (Evangelical Christian Union) 치킨파티'] = 99
    event_dict['2023 GoAheaD 9월 정기공연'] = 100

    return event_dict

def process_users(event_dict: dict) -> List[np.ndarray]:
    user_df = pd.read_csv('1023_user_data.csv')
    user_data = []
    academic_const = "academic"
    festival_const = "festival"
    academic_string = "‘과학, 생명, 예술(Science, Life, Art)’ 예비 포럼"
    festival_string = "2023 서울대학교 의류학과 패션쇼 ‘서울, SEOUL’"
    academic_column = '다음 중 관심 있는 축제(학술행사)를 골라주세요 (최소 2개)'
    festival_column = '다음 중 관심 있는 축제(공연행사)를 골라주세요  (최소 2개)'

    for _, row in user_df.iterrows():
        item_list = []
        def add_to_items(event_type: str, items: List):
            if event_type == academic_const:
                event_name = academic_string 
                column_name = academic_column
            elif event_type == festival_const:
                event_name = festival_string 
                column_name = festival_column
            else:
                raise ValueError("Invalid input: Only 'academic' or 'festival' is allowed for event_type")
            
            subindex = row[column_name].find(event_name)
            if subindex >= 0:
                items.append(event_dict[event_name])

            processed_row = row[column_name].replace(event_name, '').split(', ')
            for elem in processed_row:
                if elem in event_dict.keys():
                    items.append(event_dict[elem])
        
        add_to_items(academic_const, item_list)
        add_to_items(festival_const, item_list)
        
        item_list = np.asarray(item_list)
        user_data.append(item_list)
    
    return user_data