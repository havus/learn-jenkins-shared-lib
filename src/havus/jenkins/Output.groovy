package havus.jenkins;

class Output {
  static def sayHello(String name) {
    echo("Hi, ${name}. Welcome aboard")
  }
}
