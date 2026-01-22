import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS


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


ResponseObject response = WS.sendRequest(findTestObject('Object Repository/GetBooking', [ 'host': GlobalVariable.host ]))
println(response.getResponseText())

// Assertion: verify status code
assert response.getStatusCode() == 200 : "Status code is not 200, but ${response.getStatusCode()}"

// Assertion: verify structure (array of object with key 'bookingid')
import groovy.json.JsonSlurper
List jsonList = (List) new JsonSlurper().parseText(response.getResponseText())
assert jsonList instanceof List : "Response is not a list"
if (jsonList.size() > 0) {
	Object firstItem = jsonList.get(0)
	assert firstItem.hasProperty('bookingid') || (firstItem instanceof Map && firstItem.containsKey('bookingid')) : "First item does not have 'bookingid' key"
} else {
	println("Response list is empty, no structure to verify.")
}