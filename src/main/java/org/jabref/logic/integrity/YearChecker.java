package org.jabref.logic.integrity;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.GregorianCalendar;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.strings.StringUtil;

public class YearChecker implements ValueChecker {

    private static final Predicate<String> CONTAINS_DIGITS = Pattern.compile("[-0-9]+")
            .asPredicate();
    private static final Predicate<String> ENDS_WITH_DIGITS = Pattern.compile("[0-9]+$").asPredicate();
    private static final String PUNCTUATION_MARKS = "[(){},.;!?<>%&$]";
    private static final GregorianCalendar calendar = new GregorianCalendar();

    /**
     * Checks, if the number String contains a valid year and ends with it.
     * Official bibtex spec:
     * Generally it should consist of four numerals, such as 1984, although the standard styles
     * can handle any year whose last four nonpunctuation characters are numerals, such as ‘(about 1984)’.
     * Source: http://ftp.fernuni-hagen.de/ftp-dir/pub/mirrors/www.ctan.org/biblio/bibtex/base/btxdoc.pdf
     */
    @Override
    public Optional<String> checkValue(String value) {
        if (StringUtil.isBlank(value)) {
            return Optional.empty();
        }

        try {
            int digits = Integer.parseInt(value.replaceAll("[^-0-9]+", ""));
            if ((digits < calendar.getMinimum(GregorianCalendar.YEAR))
                || (digits > calendar.getMaximum(GregorianCalendar.YEAR))) {
                return Optional.of(Localization.lang("should contain a valid year"));
            }
        } catch (NumberFormatException e) {
            return Optional.of(Localization.lang("should contain a valid year"));
        }

        if (!CONTAINS_DIGITS.test(value.trim())) {
            return Optional.of(Localization.lang("should contain a valid year"));
        }

        if (!ENDS_WITH_DIGITS.test(value.replaceAll(PUNCTUATION_MARKS, ""))) {
            return Optional.of(Localization.lang("last nonpunctuation characters should be numerals"));
        }

        return Optional.empty();
    }
}