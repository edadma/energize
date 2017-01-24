package xyz.hyperreal.energize

import org.apache.http.HttpStatus._
import xyz.hyperreal.numbers.ComplexBigInt


object Builtins {
	val constants =
		List(
			"i" -> ComplexBigInt.i,
			"e" -> math.E,
			"pi" -> math.Pi,
			"None" -> None,
			"SC_OK" -> SC_OK,
			"SC_CREATED" -> SC_CREATED,
			"SC_NO_CONTENT" -> SC_NO_CONTENT,
			"SC_BAD_REQUEST" -> SC_NOT_FOUND,
			"SC_NOT_ACCEPTABLE" -> SC_NOT_ACCEPTABLE
		)
	
	def pairs( natives: List[Native] ) = natives map (n => n.name -> n)
	
	val map =
		pairs( Native(QueryFunctions) ) ++
		pairs( Native(CommandFunctions) ) ++
		pairs( Native(ResultFunctions) ) ++
		pairs( Native(SupportFunctions) ) ++
		constants toMap
	
	val routes =
		"""
		|route <base>/<resource>
		|  GET     /id:long                   OkSingleOrNotFound( findID(<resource>, id, ?fields, None, None, None), "<resource>", id )
		|  GET     /                          Ok( "<resource>", list(<resource>, ?fields, ?filter, ?order, ?page, ?start, ?limit) )
		|  POST    /                          Created( "<resource>", insert(<resource>, json) )
		|  PATCH   /id:long                   OkAtLeastOneOrNotFound( update(<resource>, id, json, false) )
		|  PUT     /id:long                   OkAtLeastOneOrNotFound( update(<resource>, id, json, true) )
		|  DELETE  /id:long                   OkAtLeastOneOrNotFound( delete(<resource>, id) )
		""".stripMargin

	val mtmroutes =
		"""
		|route <base>/<resource>
		|  GET     /id:long/field:            OkSingleOrNotFound( findIDMany(<resource>, id, field, ?page, ?start, ?limit), "<resource>", id )
		|  POST    /id:long/field:            Created( "<resource>", append(<resource>, id, field, json) )
		|  POST    /sid:long/field:/tid:long  appendIDs( <resource>, sid, field, tid ); NoContent()
		|  DELETE  /id:long/field:            deleteLinks( <resource>, id, field, json ); NoContent()
		|  DELETE  /id:long/field:/tid:long   OkAtLeastOneOrNotFound( deleteLinksID(<resource>, id, field, tid) )
		""".stripMargin

	//insertLinks(<resource>, id, field, json)

	val special =
		"""
		|route /meta
		|  DELETE  /res:                      dataResult( res, deleteResource(res) )
		|
		|route <auth>
		|  GET     /login                     login()
		|  GET     /logout                    logout()
		|  GET     /register                  register()
		""".stripMargin
}
