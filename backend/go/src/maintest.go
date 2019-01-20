package main
 
import (
    "net/http"
    "io/ioutil"

    "log"
)
 
func main() {

    client := &http.Client{}
    req, err := http.NewRequest("GET", "http://127.0.0.1:3000/message", nil)
    req.Header.Add("Content-Type", "application/x-protobuf")
    resp, err := client.Do(req)
    if err != nil {
        panic(err) 
    }
    defer resp.Body.Close()
 
    bodyBytes, err := ioutil.ReadAll(resp.Body)
    if err != nil {
        panic(err)
    }
 
    bodyString := string(bodyBytes)

    log.Printf(bodyString)
}