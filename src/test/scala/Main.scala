package xyz.hyperreal.cras

import java.io.File


object Main extends App {

	def test {
		val (c, s) = dbconnect( "test", true )
		val env = configure( io.Source.fromFile("sum.cras"), c, s )
		
		println( process("GET", "/eval", """ {"expr": "3//2"} """, env) )
		
		c.close
	}
	
	test
}