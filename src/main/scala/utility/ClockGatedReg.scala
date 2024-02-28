package utility

import chisel3._
import chisel3.util._

object GatedValidRegNext {
  def apply(next: Bool, init: Bool = false.B): Bool = {
    val last = Wire(next.cloneType)
      last := RegEnable(next, init, next || last)
      last
    }
}

class GatedValidRegNextN(n: Int) extends Module {
  val io = IO(new Bundle() {
    val in  = Input(Bool())
    val out = Output(Bool())
  })
  var out = io.in
  for (i <- 0 until n) {
    out = GatedValidRegNext(out)
  }
  io.out := out
}

object GatedValidRegNextN {
  def apply(next: Bool, n: Int): Bool = {
    val gatedValidRegNext = Module(new GatedValidRegNextN(n))
      gatedValidRegNext.io.in := next
      gatedValidRegNext.io.out
  }
}