// Reqeust Body 검색

POST movie_search/_search
{
    "query": {
        "query_string": {
            "default_field": "movieNmEn",
            "query": "Family"
        }
    }
}


POST movie_search/_search
{
    "query": {
        "query_string": {
            "default_field": "movieNmEn",
            "query": "movieNmEn: * OR prdtYear:2017"
        }
    },
    "from": 0,
    "size": 5,
    "sort": [
        {
            "_score": {
                "order": "desc"
            },
            "movieCd": {
                "order": "asc"
            }
        }
    ],
    "_source": [
        "movieCd",
        "movieNm",
        "movieNmEn",
        "typeNm",
        "prdtYear"
    ]
}


POST movie_search/_search
{
    "query": {
        "match": {
            "movieNm": "기묘한 가족"
        }
    }
}

POST movie_search/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "match_all": {}
                }
            ],
            "filter": {
                "term": {
                    "repGenreNm": "다큐멘터리"
                }
            }
        }
    }
}

// 첫 번째 페이지 요청
POST movie_search/_search
{
    "from": 0,
    "size": 5,
    "query": {
        "term": {
            "repNationNm": "한국"
        }
    }
}

// 두 번째 페이지 요청
POST movie_search/_search
{
    "from": 5,
    "size": 5,
    "query": {
        "term": {
            "repNationNm": "한국"
        }
    }
}


// 쿼리 결과 정렬
POST movie_search/_search
{
    "query": {
        "term": {
            "repNationNm": "한국"
        }
    },
    "sort": {
        "prdtYear": {
            "order": "asc"
        },
        "_score": {
            "order": "desc"
        }
    }
}

// _source 필드 필터링
POST movie_search/_search
{
    "_source": [
        "movieNm"
    ],
    "query": {
        "term": {
            "repNationNm": "한국"
        }
    }
}

// 범위 검색
POST movie_search/_search
{
    "query": {
        "range": {
            "prdtYear": {
                "gte":"2016",
                "lte":"2017"
            }
        }
    }
}

// operator 설정(AND OR)
POST movie_search/_search
{
    "query": {
        "match": {
            "movieNm": {
                "query": "여기는 한국!",
                "operator": "and"
            }
        }
    }
}

// fuzziness 설정
POST movie_search/_search
{
    "query": {
        "match": {
            "movieNmEn": {
                "query": "Fli High",
                "fuzziness": 1
            }
        }
    }
}

// boost 설정
POST movie_search/_search
{
    "query": {
        "multi_match": {
            "query": "Fly", 
            "fields": ["movieNm^3", "movieNmEn"]
        }
    }
}