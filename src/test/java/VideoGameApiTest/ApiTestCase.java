package VideoGameApiTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;



public class ApiTestCase 
{
	@Test(priority = 1)
	public void Get_AllVideoGames()
	{
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
		
	}
    @Test(priority = 2)
	public void Create_NewGame()
	{
		HashMap data = new HashMap();
		data.put("id",100);
		data.put("name", "spider-man");
		data.put("releaseDate", "2021-03-16");
		data.put("reviewScore", 5);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res=
		given()
		.contentType("application/json")
		.body(data)
		
		.when().post("http://localhost:8080/app/videogames")
		
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}	
	
	@Test(priority = 3)
	public void Get_AddedGameById()
	{
		
		given()
		.when()
		.get("http://localhost:8080/app/videogames/100")
		.then()
		.statusCode(200)
		.log().body()
		
	    .body("videoGame.id",equalTo("100"))
	    .body("videoGame.name",equalTo("spider-man"));
	}
	
	@Test(priority = 4)
	public void Update_AddedGame()
	{
		HashMap data = new HashMap();
		data.put("id",100);
		data.put("name", "pacman");
		data.put("releaseDate", "2021-03-16");
		data.put("reviewScore", 4);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		
		given()
		.contentType("application/json")
		.body(data)
		
		.when()
		.put("http://localhost:8080/app/videogames/100")
		
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id",equalTo("100"))
	    .body("videoGame.name",equalTo("pacman"));
	}
	
	@Test(priority = 5)
	public void Delete_AddedGame()
	{
		Response res = 
		given()
		.when()
		.delete("http://localhost:8080/app/videogames/100")
		.then()
		.log().body()
		.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
		
	}
	
}
