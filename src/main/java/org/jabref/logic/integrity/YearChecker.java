package org.jabref.logic.integrity;

import java.util.GregorianCalendar;
import java.util.Optional;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.strings.StringUtil;

public class YearChecker implements ValueChecker {

    private static final GregorianCalendar calendar = new GregorianCalendar();

    /**
     * Checks if year value is numeric and valid within Gregorian Calendar limits
     **/
    @Override
    public Optional<String> checkValue(String value) {
        if (StringUtil.isBlank(value)) {
            return Optional.empty();
        }
        try {
            if ((Integer.parseInt(value.trim()) < calendar.getMinimum(GregorianCalendar.YEAR))
                    || (Integer.parseInt(value.trim()) > calendar.getMaximum(GregorianCalendar.YEAR))) {
                return Optional.of(Localization.lang("should be a valid year (numbers only)"));
            }
        }
        catch (NumberFormatException e) {
                return Optional.of(Localization.lang("should be a valid year (numbers only)"));
            }
        return Optional.empty();
    }
}
