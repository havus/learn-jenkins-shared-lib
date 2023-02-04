package havus.jenkins;

class Output {
  static def sayHello(String name) {
    // code dibawah tidak akan diprint di jenkins karena beda environment code
    // pakai sayHelloWithStep untuk bisa dilihat di jenkins log
    println("In Library - Start")
    println("Hi, ${name}. Welcome aboard")
    println("In Library - End")
  }

  static def sayHelloWithStep(steps, String name) {
    steps.echo("In Library - Start")
    steps.echo("Hi, ${name}. Welcome aboard")
    steps.echo("In Library - End")
  }
}
