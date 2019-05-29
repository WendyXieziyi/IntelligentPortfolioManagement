import tushare as ts

list = ts.guba_sina(True)
list.to_json('/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/jsonData/news.json',orient='records',force_ascii=False)