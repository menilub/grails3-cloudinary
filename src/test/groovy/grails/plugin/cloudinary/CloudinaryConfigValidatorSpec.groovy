package grails.plugin.cloudinary

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(CloudinaryService)
class CloudinaryConfigValidatorSpec extends Specification {

	def 'test  cloudinary config validation'() {

		setup:
		CloudinaryConfig cloudinaryConfig = new CloudinaryConfig(apiKey: apiKey, apiSecret: apiSecret, cloudName: cloudName)

		expect:
		valid == CloudinaryConfigValidator.validate(cloudinaryConfig)

		where:
		apiKey | apiSecret | cloudName || valid
		'1'    | '1'       | 'C'       || true
		null   | '1'       | 'C'       || false
		'1'    | null      | 'C'       || false
		'1'    | '1'       | null      || false
		null   | null      | null      || false
	}
}
