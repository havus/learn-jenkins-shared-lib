def sayHello() {
  echo('Hello world')
}

def sayHelloToAll(List names) {
  for (name in names) {
    echo "Hi, ${name}. Welcome aboard"
  }
}