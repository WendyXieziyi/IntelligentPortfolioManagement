import tushare as ts

df = ts.get_index()
df.to_json('/Users/apple/Desktop/Portfolio/portfolio_server/src/main/resources/static/jsonData/index.json',orient='records',force_ascii=False)