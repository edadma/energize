package xyz.hyperreal.energize

import org.scalatest._
import prop.PropertyChecks

import org.apache.http.HttpStatus._


class JSONConfigTests extends FreeSpec with PropertyChecks with Matchers {

	"basic" in {
		val (c, s, d) = Test.dbconnect
		val key = AUTHORIZATION.getString( "key" )
		val config =
			"""
				|{
				|  "tables": [
				|    {
				|      "name": "r",
				|      "resource": true,
				|      "fields": [
				|        {
				|          "name": "limit",
				|          "type": {
				|            "category": "primitive",
				|            "type": "integer"
				|          }
				|        },
				|        {
				|          "name": "asdf",
				|          "type": {
				|            "category": "primitive",
				|            "type": "boolean"
				|          }
				|        }
				|      ]
				|    }
				|  ]
				|}
			""".trim.stripMargin
		val env = Energize.configureFromJSON( io.Source.fromString( config ), c, s, d, key )

		env.process( "POST", "/r", """{"limit": 123, "asdf": true}""" ) shouldBe
			(SC_CREATED, "application/json",
				"""
					 |{
					 |  "data": 1
					 |}
				 """.trim.stripMargin )
		env.process( "GET", "/r", null ) shouldBe
			(SC_OK, "application/json",
				"""
					|{
					|  "data": [
					|    {
					|      "_id": 1,
					|      "limit": 123,
					|      "asdf": true
					|    }
					|  ]
					|}
				""".trim.stripMargin )
	}

	"simple js" in {
		val (c, s, d) = Test.dbconnect
		val key = AUTHORIZATION.getString( "key" )
		val config =
			"""
				|{
				|  "routes": [
				|    {
				|      "mappings": [
				|        {
				|          "method": "GET",
				|          "path": "/js",
				|          "language": "ECMAScript",
				|          "action": "Ok( 1 + 2 )"
				|        }
				|      ]
				|    }
				|  ]
				|}
			""".trim.stripMargin
		val env = Energize.configureFromJSON( io.Source.fromString( config ), c, s, d, key )

		env.process( "GET", "/js", null ) shouldBe
			(SC_OK, "application/json",
				"""
					|{
					|  "data": 3
					|}
				""".trim.stripMargin )
	}
}