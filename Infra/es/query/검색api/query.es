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


