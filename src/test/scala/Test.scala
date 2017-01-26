package xyz.hyperreal.energize

import java.io.{PrintStream, ByteArrayOutputStream}


object Test {

  def dbconnect = Energize.dbconnect( "H2", "org.h2.Driver", "jdbc:h2:mem:", "sa", "" )
//		Class.forName( "org.h2.Driver" )
//
//		val connection = DriverManager.getConnection( "jdbc:h2:mem:test", "sa", "" )
//
//		(connection, connection.createStatement)
//	}

	def capture( code: String ) = {
		val buf = new ByteArrayOutputStream
		
		Console.withOut( new PrintStream(buf) )( Energize.configure(io.Source.fromString(code), null, null, null) )
		buf.toString.trim
	}
	
	def captureReturn( code: String ) = {
		val buf = new ByteArrayOutputStream
		val ret = Console.withOut( new PrintStream(buf) )( Energize.configure(io.Source.fromString(code), null, null, null) )
		
		(ret, buf.toString.trim)
	}
}