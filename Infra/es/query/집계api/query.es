
// 합산 집계

GET /apache-web-log/_search?size=0
{
    "aggs": {
        "total_bytes": {
            "sum": {
                "field":"bytes"
            }
        }
    }
}


GET /apache-web-log/_search?size=0
{
    "query": {
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.city_name": "Paris"
                }
            }
        }
    },
    "aggs": {
        "total_bytes": {
            "sum": {
                "field":"bytes"
            }
        }
    }
}

GET /apache-web-log/_search?size=0
{
    "query": {
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.city_name": "Paris"
                }
            }
        }
    },
    "aggs": {
        "total_bytes": {
            "sum": {
               "script": {
                "lang":"painless",
                "source": "doc.bytes.value"
               }
            }
        }
    }
}

GET /apache-web-log/_search?size=0
{
    "query": {
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.city_name": "Paris"
                }
            }
        }
    },
    "aggs": {
        "total_bytes": {
            "sum": {
               "script": {
                "lang":"painless",
                "source": "doc.bytes.value / params.divide_value",
                "params": {
                    "divide_value": 1024
                }
               }
            }
        }
    }
}

GET /apache-web-log/_search?size=0
{
    "query": {
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.city_name": "Paris"
                }
            }
        }
    },
    "aggs": {
        "total_bytes": {
            "sum": {
               "script": {
                "lang":"painless",
                "source": "doc.bytes.value / params.divide_value",
                "params": {
                    "divide_value": 1024
                }
               }
            }
        }
    }
}