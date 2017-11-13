package grails.plugin.cloudinary

class CloudinaryUploadResult {
    String publicId
    long version
    String signature
    int width
    int height
    String format
    String resourceType
    Date createdAt
    long bytes
    String type
    String url
    String secureUrl

    @Override
    String toString() {
        getClass().simpleName + '{' + metaClass.properties.findAll { it.name != 'class' }.collect {
            def value = this[it.name]
            String quote = it.type == String && value != null ? "'" : ''
            it.name + '=' + quote + value + quote
        }.join(', ') + '} ' + super.toString()
    }
}
