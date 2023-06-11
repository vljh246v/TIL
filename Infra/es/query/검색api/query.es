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
            "movieNm": "가족"
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

// Query DSL의 주요 쿼리

// Match All Query
POST movie_search/_search
{
    "query": {
        "match_all": {}
    }
}

// Match Query
POST movie_search/_search
{
    "query": {
        "match": {
            "movieNm": "그대 장미"
        }
    }
}

// multi match query
POST movie_search/_search
{
    "query": {
        "multi_match": {
            "query": "가족",
            "fields": [
                "movieNm",
                "movieNmEN"
            ]
        }
    }
}


// Term Query
POST movie_search/_search
{
    "query": {
        "term": {
            "genreAlt": "코미디"
        }
    }
}

// Bool Query
POST movie_search/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "term": {
                        "repGenreNm": "코미디"
                    }
                },
                {
                    "match": {
                        "repNationNm": "한국"
                    }
                }
            ],
            "must_not": [
                {
                    "match": {
                        "typeNm": "단편"
                    }
                }
            ]
        }
    }
}

// Query String
POST movie_search/_search
{
    "query": {
        "query_string": {
            "default_field": "movieNm",
            "query": "(어린이 날) AND (바른)"
        }
    }
}

// Perfix Query
POST movie_search/_search
{
    "query": {
        "prefix": {
            "movieNm" : "자전차"
        }
    }
}

// exists Query
POST movie_search/_search
{
    "query": {
        "exists": {
            "field": "movieNm "
        }
    }
}

// wildcard query
POST movie_search/_search
{
    "query": {
        "wildcard": {
            "typeNm": "장?"
        }
    }
}


// Nested Query
PUT movie_nested
{
    "settings": {
        "number_of_replicas": 1,
        "number_of_shards": 5
    },
    "mappings": {
        "properties": {
            "repGenreNm": {
                "type": "keyword"
            },
            "companies": {
                "type": "nested",
                "properties": {
                    "companyCd": {
                        "type": "keyword"
                    },
                    "companyNm": {
                        "type": "keyword"
                    }
                }
            }
        }
    }
}

PUT movie_nested/_doc/1
{
    "movieCd": "201846",
    "repGenreNm":"멜로/로맨스",
    "companies": [
        {
            "companyCd":"20173401",
            "companyNm":"(주)케이피에이기획"
        }
    ]
}


GET movie_nested/_search
{
    "query":{
        "bool": {
            "must": [
                {
                    "term":{
                        "repGenreNm":"멜로/로맨스"
                    }
                },
                {
                    "nested": {
                        "path": "companies",
                        "query": {
                            "bool": {
                                "must": [
                                    {
                                        "term": {
                                            "companies.companyCd": "20173401"
                                        }
                                    }
                                ]
                            }
                        }
                    }
                }
            ]
        }
    }
}

// count api
POST movie_search/_count
{
    "query": {
        "query_string": {
            "default_field":"prdtYear",
            "query": "2017"
        }
    }
}


// explain api
POST movie_search/_search
{
    "query": {
        "term": {
            "prdtYear": "2017"
        }
    }
}


POST movie_search/_doc/eDzJqmkBjjM-ebDb8PsR/_explain
{
    "query": {
        "term": {
            "prdtYear": "2017"
        }
    }
}

// Profile API
POST movie_search/_search
{
    "profile": true,
    "query": {
        "match_all": {}
    }
}