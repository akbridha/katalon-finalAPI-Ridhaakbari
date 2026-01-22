import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper
import com.kms.katalon.core.testobject.ResponseObject


// Import ResponseObject untuk tipe data response


ResponseObject response = WS.sendRequest(findTestObject('Object Repository/CreateToken',  [ 'host': GlobalVariable.host ]))

// Assertion: verify status code
assert response.getStatusCode() == 200 : "Status code is not 200, but ${response.getStatusCode()}"

//  response ke console
println(response.getResponseText())

// Ambil token dari response dan assign ke GlobalVariable
Object json = new JsonSlurper().parseText(response.getResponseText())
if (json.hasProperty('token') || (json instanceof Map && json.containsKey('token'))) {
	String tokenValue = json instanceof Map ? json.get('token') : json.token
	GlobalVariable.token = tokenValue
	println("Token assigned to GlobalVariable.token: ${tokenValue}")
	// Assertion: verify token is not empty
	assert tokenValue != null && !tokenValue.isEmpty() : "Token is empty or null"
} else {
	println("Token not found in response.")
	assert false : "Token not found in response"
}

