
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

GET /_cat/indices/apache*?v&pretty

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
                "source": "doc.bytes.value / (double)params.divide_value",
                "params": {
                    "divide_value": 1024
                }
               }
            }
        }
    }
}

// 평균값 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "avg_bytes": {
            "avg": {
                "field": "bytes"
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
        "avg_bytes": {
            "avg": {
                "field": "bytes"
            }
        }
    }
}


// 최솟값 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "min_bytes": {
            "min" : {
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
                    "geoip.city_name":"Paris"
                }
            }
        }
    },
    "aggs": {
        "min_bytes": {
            "min" : {
                "field":"bytes"
            }
        }
    }
}

// 최댓값 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "max_bytes": {
            "max": {
                "field":"bytes"
            }
        }
    }
}


GET /apache-web-log/_search?size=0
{
    "query":{
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.city_name": "Paris"
                }
            }
        }
    },
    "aggs": {
        "max_bytes": {
            "max": {
                "field":"bytes"
            }
        }
    }
}

// 개수 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_count": {
            "value_count": {
                "field": "bytes"
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
        "bytes_count": {
            "value_count": {
                "field": "bytes"
            }
        }
    }
}

// 통계 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_stats": {
            "stats": {
                "field": "bytes"
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
        "bytes_stats": {
            "stats": {
                "field": "bytes"
            }
        }
    }
}

// 확장 통계 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_extended_stats": {
            "extended_stats": {
                "field": "bytes"
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
        "bytes_extended_stats": {
            "extended_stats": {
                "field": "bytes"
            }
        }
    }
}

// 카디널리티 집계
GET /apache-web-log/_search?size=0
{
    "query": {
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.country_name": "United States"
                }
            }
        }
    },
    "aggs": {
        "us_city_names": {
            // "value_count": {
            //     "field": "geoip.city_name.keyword"
            // }
            "cardinality": {
                "field": "geoip.city_name.keyword"
            }
        }
    }
}

// 백분위 수 집계
GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_percentiles": {
            "percentiles": {
                "field": "bytes"
            }
        }
    }
}

GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_percentiles": {
            "percentiles": {
                "field": "bytes",
                "percents": [10, 20, 30, 40, 50, 60, 70, 80, 90]
            }
        }
    }
}

// 백분위 수 랭크 집계

GET /apache-web-log/_search?size=0
{
    "aggs": {
        "bytes_percentile_ranks": {
            "percentile_ranks": {
                "field": "bytes",
                "values": [5000, 10000]
            }
        }
    }
}

// 지형 경계 집계
GET /_mapping
POST /_snapshot/apache-web-log/applied-mapping/_restore
GET /_cat/indices/apache*?v&pretty
GET /apache-web-log-applied-mapping/_mapping/field/geoip.location

GET /apache-web-log-applied-mapping/_search?size=0
{
    "aggs": {
        "viewport": {
            "geo_bounds": {
                "field": "geoip.location",
                "wrap_longitude": true
            }
        }
    }
}

GET /apache-web-log-applied-mapping/_search?size=0
{
    "query":{
        "constant_score": {
            "filter": {
                "match": {
                    "geoip.continent_code": "EU"
                }
            }
        }
    },
    "aggs": {
        "viewport": {
            "geo_bounds": {
                "field": "geoip.location",
                "wrap_longitude": true
            }
        }
    }
}

// 지형 중심 집계
GET /apache-web-log-applied-mapping/_search?size=0
{
    "aggs": {
        "centroid": {
            "geo_centroid": {
                "field": "geoip.location"
            }
        }
    }
}