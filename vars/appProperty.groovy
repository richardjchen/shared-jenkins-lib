// vars/appProperty.groovy

import org.jenkinsci.plugins.pipeline.utility.steps.shaded.org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder


def textEncodeBase64(strEncode) {

    def encodedValue = Base64Coder.encodeString(strEncode)
    println "encodedValue--> ${encodedValue}"
    
    return encodedValue  
}

def textDecodeBase64(strDecode) {

    def decodedValue = Base64Coder.decodeString(strDecode) 
    println "decodedValue--> ${decodedValue}"
    
    return decodedValue
}

def getProperties(envfile, encodedName) {
  
	 def keyValue = " "
	 def exists = fileExists envfile
	 def properties = null
	 def decodedName = Base64Coder.decodeString(encodedName)
	
	 println "jenkins.properties encoded key name: ${encodedName}"
	 println "jenkins.properties encoded key name: ${decodedName}"
	
	 if (exists){
    	       println "jenkins.properties file exists"
    	       properties = readProperties file: envfile

	       if (properties.size() > 0){
    		    println "jenkins.properties value exists"
		    keys= properties.keySet()
		    keyValue = properties[decodedName]
		    
		    println "set them up as sytem environment variables for application to grab the value"
		       
		    keys= properties.keySet()
                    for(key in keys) {
                        value = properties["${key}"]
			env."${key}" = "${value}"
                        println "populate them as sytem environment variables: ${value}"
                    }
		       
	        } else {
	            println "jenkins.properties does not exist"
	        }     	    
    	       
	 } else {
	       println "jenkins.properties does not exist"
	 }
	
	 println "jenkins.properties keyValue: ${keyValue}"
	
	 def encodedValue = textEncodeBase64(keyValue)
	
	 println "jenkins.properties encoded keyValue: ${encodedValue}"
	
         return encodedValue
}
