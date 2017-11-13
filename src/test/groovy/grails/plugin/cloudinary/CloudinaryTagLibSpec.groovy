package grails.plugin.cloudinary

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import spock.lang.Specification

@TestFor(CloudinaryTagLib)
class CloudinaryTagLibSpec extends Specification {

    CloudinaryService cloudinaryService = new CloudinaryService()

    def setup() {
        tagLib.cloudinaryService = cloudinaryService
    }

    void "test img tag"() {
        setup:
        cloudinaryService.cloudinaryConfig = new CloudinaryConfig(cloudName: 'test')

        expect:
        applyTemplate('<cl:img id="1" />') == '''<img src='http://res.cloudinary.com/test/image/upload/1' id='1'/>'''
        applyTemplate('<cl:img id="1" width="100" />') == '''<img src='http://res.cloudinary.com/test/image/upload/w_100/1' id='1' width='100'/>'''
        applyTemplate('<cl:img id="1" width="100" height="150" />') == '''<img src='http://res.cloudinary.com/test/image/upload/h_150,w_100/1' height='150' id='1' width='100'/>'''
        applyTemplate('<cl:img id="1" width="100" height="150" format="png" />') == '''<img src='http://res.cloudinary.com/test/image/upload/h_150,w_100/1.png' format='png' height='150' id='1' width='100'/>'''
        applyTemplate('<cl:img id="1" width="100" height="150" format="png" crop="fill" />') == '''<img src='http://res.cloudinary.com/test/image/upload/c_fill,h_150,w_100/1.png' crop='fill' format='png' height='150' id='1' width='100'/>'''
    }

    void "test src tag"() {
        setup:
        cloudinaryService.cloudinaryConfig = new CloudinaryConfig(cloudName: 'test')

        expect:
        applyTemplate('<cl:src id="1" />') == '''http://res.cloudinary.com/test/image/upload/1'''
        applyTemplate('<cl:src id="1" width="100" />') == '''http://res.cloudinary.com/test/image/upload/w_100/1'''
        applyTemplate('<cl:src id="1" width="100" height="150" />') == '''http://res.cloudinary.com/test/image/upload/h_150,w_100/1'''
        applyTemplate('<cl:src id="1" width="100" height="150" format="png" />') == '''http://res.cloudinary.com/test/image/upload/h_150,w_100/1.png'''
        applyTemplate('<cl:src id="1" width="100" height="150" format="png" crop="fill" />') == '''http://res.cloudinary.com/test/image/upload/c_fill,h_150,w_100/1.png'''
    }

    void "test img tag with invalid CloudinaryConfig"() {
        setup:
        cloudinaryService.cloudinaryConfig = new CloudinaryConfig()

        when:
        applyTemplate('<cl:img id="1" />')

        then:
        GrailsTagException e = thrown(GrailsTagException)
        e.message == 'Error executing tag <cl:img>: Must supply cloud_name in tag or in configuration'
    }

    void "test src tag with invalid CloudinaryConfig"() {
        setup:
        cloudinaryService.cloudinaryConfig = new CloudinaryConfig()

        when:
        applyTemplate('<cl:src id="1" />')

        then:
        GrailsTagException e = thrown(GrailsTagException)
        e.message == 'Error executing tag <cl:src>: Must supply cloud_name in tag or in configuration'
    }
}
