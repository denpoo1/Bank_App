fetch('https://reqbin.com/echo/get/json', {
  headers: {Authentication: 'Bearer {token}'}
})
   .then(resp => resp.json())
   .then(json => console.log(JSON.stringify(json)))