import requests
from requests.exceptions import ConnectionError
import concurrent.futures
import time, datetime

CONNECTIONS = 5

def access_page(cnt):
  try:
    r = requests.get('http://localhost:8090/')
    print(">> [%02d] (Code: %d) %s"%(cnt, r.status_code, r.text))
  except ConnectionError:
    print(">> [%02d] Connection error!"%(cnt));

def run(CONNECTIONS):
  pool = concurrent.futures.ThreadPoolExecutor(max_workers = 10)
  threads = []
  for i in range(CONNECTIONS):
    threads.append(pool.submit(access_page, i))
  concurrent.futures.wait(threads)

start = time.time()
run(CONNECTIONS)
end = time.time()

elapsed = datetime.timedelta(seconds=(end-start))
print(f"Elapsed time: {elapsed}"); 



