package org.inspektr.common.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.inspektr.common.annotation.RegExp;

/**
 * Validates regular expression annotations.
 * 
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0
 *
 */
public final class RegExpAnnotationValidator extends AbstractAnnotationValidator {

	private final Map<String, Pattern> rules;
	
	public RegExpAnnotationValidator(final Map<String, String> patterns) {
		final Map<String, Pattern> rules = new HashMap<String, Pattern>();
		
		for (final Entry<String, String> me : patterns.entrySet()) {
			rules.put(me.getKey(), Pattern.compile(me.getValue()));
		}
		
		this.rules = rules;
	}
		
	protected void validateInternal(final Annotation annotation, final Object arg,
			final String type, final String fieldName, final String objectName) {
		final RegExp r = (RegExp) annotation;
		
		final Pattern pattern = rules.get(r.rule());
		
		if (pattern == null) {
			throw new IllegalStateException("Regular Expression rule " + r.rule() + " does not exist.");
		}
		
		if (pattern.matcher((String) arg).matches()) {
			return;
		}
		
		throw new IllegalStateException(type + " " + fieldName + " does not match regular expression on " + objectName);
	}

	public Class<? extends Annotation> supports() {
		return RegExp.class;
	}
}
