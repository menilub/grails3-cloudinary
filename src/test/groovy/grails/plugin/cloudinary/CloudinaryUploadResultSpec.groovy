package grails.plugin.cloudinary

import groovy.json.JsonSlurper
import spock.lang.Specification

class CloudinaryUploadResultSpec extends Specification {

    def 'test cloudinary upload result parsing'() {
        setup:
        String rawJsonResult = '''
            {
            "public_id":"tquyfignx5bxcbsupr6a",
            "version":1375302801,
            "signature":"52ecf23eeb987b3b5a72fa4ade51b1c7a1426a97",
            "width":1920,
            "height":1200,
            "format":"jpg",
            "resource_type":"image",
            "created_at":"2013-07-31T20:33:21Z",
            "bytes":737633,
            "type":"upload",
            "url": "http://res.cloudinary.com/demo/image/upload/v1375302801/tquyfignx5bxcbsupr6a.jpg",
            "secure_url": "https://res.cloudinary.com/demo/image/upload/v1375302801/tquyfignx5bxcbsupr6a.jpg"
        }
        '''

        when:
        def json = new JsonSlurper().parseText(rawJsonResult)
        CloudinaryUploadResult result = CloudinaryService.toCloudinaryUploadResult(json)

        then:
        result.publicId == json.'public_id'
        result.version == json.version
        result.signature == json.signature
        result.url == json.url
        result.secureUrl == json.'secure_url'
        result.width == json.width
        result.height == json.height
        result.format == json.format
        result.resourceType == json.'resource_type'
        result.bytes == json.bytes
        result.type == json.type

        // Verify date parsing
        Calendar calendar = result.createdAt.toCalendar()
        calendar.get(Calendar.YEAR) == 2013
        calendar.get(Calendar.MONTH) == 6
        calendar.get(Calendar.DAY_OF_MONTH) == 31
        calendar.get(Calendar.HOUR_OF_DAY) == 20
        calendar.get(Calendar.MINUTE) == 33
        calendar.get(Calendar.SECOND) == 21
    }
}
