package grails.plugin.cloudinary

import com.cloudinary.Transformation
import com.cloudinary.Url

class CloudinaryTagLib {

//    static defaultEncodeAs = 'html'
//
//    static encodeAsForTags = [img: 'raw']

    static namespace = "cl"

    CloudinaryService cloudinaryService

    /**
     * Renders an image tag by creating a transformation.
     */
    def img = { attrs ->
        out << getUrlForAttributes(attrs).imageTag(attrs.id, attrs)
    }

    def vid = { attrs ->
        out << getUrlForAttributes(attrs).videoTag(attrs.id, attrs)
    }

    /**
     * Renders the url of an image.
     */
    def src = { attrs ->
        out << getUrlForAttributes(attrs).generate(attrs.id)
    }

    def urlTag = { attrs ->
        out << cloudinaryService.cloudinary.url().imageTag(attrs.remove('id'), attrs)
    }

    /**
     * Returns a url object populated with right format, transformation and other
     * cloudinary related url artifacts.
     */
    protected Url getUrlForAttributes(attrs) {
        String format = attrs.format
        String width = attrs.width
        String height = attrs.height
        String crop = attrs.crop
        String effect = attrs.effect
        String gravity = attrs.gravity
        String background = attrs.background

        Url url = cloudinaryService.cloudinary.url()

        if(format) url.format(format)

        if(width || height || crop || effect || gravity || background) {
            Transformation transformation = new Transformation()
            if(width) transformation.width(width)
            if(height) transformation.height(height)
            if(crop) transformation.crop(crop)
            if(effect) transformation.effect(effect)
            if(gravity) transformation.gravity(gravity)
            if(background) transformation.background(background)
            url.transformation(transformation)
        }

        return url
    }
}
