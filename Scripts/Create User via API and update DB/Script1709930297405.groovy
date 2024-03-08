import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.apache.commons.lang.RandomStringUtils

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

String username = 'katalondemouser@' + RandomStringUtils.randomAlphanumeric(5)
String password = 'password@1_' + RandomStringUtils.randomAlphanumeric(5)
int age = Math.random() *100

Random random = new Random()
int randomInteger = random.nextInt(2)
List genders = ['FEMALE', 'MALE']
String gender = genders[randomInteger]

response = WS.sendRequest(findTestObject('Object Repository/POST a new user', [('age') : age, ('gender') : gender, ('username') : username, ('password') : password]))

WS.verifyResponseStatusCode(response, 200)

WS.verifyElementPropertyValue(response, 'age', age)
WS.verifyElementPropertyValue(response, 'gender', gender)
WS.verifyElementPropertyValue(response, 'username', username)
WS.verifyElementPropertyValue(response, 'password', password)

globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeUpdate'(globalConnection,
	"INSERT INTO  dbo.curaHealthcare (username,password,age,gender) "
	+ " VALUES ('"+username+"','"+password+"','"+age+"','"+gender+"')"
	

)

userData = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "SELECT * FROM dbo.curaHealthcare")

CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.exportToCSV'(userData, RunConfiguration.getProjectDir()+ '/Data Files/Cura Comments - Sheet1.csv')