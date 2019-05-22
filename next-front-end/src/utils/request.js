// ant-design-pro request.js file with MIT license

/**
 * @description error
 * @typedef {Object} StatusException
 * @property {string} name status code
 * @property {string} message 消息
 * @property {{code:string,message:string,body:{code:number,message:string}}} response 返回值
 */

/**
 * 判断是否抛异常
 * @param response
 * @returns {*}
 * @throws StatusException
 */
const checkStatus = async response => {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  let responseBody = {};
  try {
    responseBody = await response.json();
  } catch (e) {
    console.log(e);
  }
  console.log(responseBody);
  const error = new Error();
  error.name = response.status;
  error.message = responseBody.message;
  error.body = responseBody;
  throw error;
};

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {{method:string,body}} [options] The options we want to pass to "fetch"
 * @return {Promise<*>}           An object containing either "data" or "err"
 * @throws StatusException
 */
export default async function request(url, options) {
  const defaultOptions = {
    credentials: "include"
  };
  const newOptions = { ...defaultOptions, ...options };
  if (
    newOptions.method === "POST" ||
    newOptions.method === "PUT" ||
    newOptions.method === "DELETE"
  ) {
    if (!(newOptions.body instanceof FormData)) {
      newOptions.headers = {
        Accept: "application/json",
        "Content-Type": "application/json; charset=utf-8",
        ...newOptions.headers
      };
      newOptions.body = JSON.stringify(newOptions.body);
    } else {
      // newOptions.body is FormData
      newOptions.headers = {
        Accept: "application/json",
        ...newOptions.headers
      };
    }
  }
  const response = await fetch(url, newOptions);
  try {
    await checkStatus(response);
  } catch (e) {
    console.log(e);
    throw e;
  }

  if (response.status === 204) {
    return response.text();
  }
  const body = await response.json();

  return { ...body };
}
