package org.jabref.model.groups;

import java.util.Objects;

/**
 * Matches entries based on a search phrase relative to the content in a specified field.
 */
public abstract class KeywordGroup extends AbstractGroup {
    protected final String searchField;
    protected final String searchExpression;
    protected final boolean caseSensitive;

    public KeywordGroup(String name, GroupHierarchyType context, String searchField, String
            searchExpression, boolean caseSensitive) {
        super(name, context);
        this.caseSensitive = caseSensitive;
        this.searchField = searchField;
        this.searchExpression = searchExpression;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public String getSearchExpression() {
        return searchExpression;
    }

    public String getSearchField() {
        return searchField;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if ((other == null) || (getClass() != other.getClass())) {
            return false;
        }
        AbstractGroup that = (AbstractGroup) other;
        return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description)
                && Objects.equals(this.context, that.context);
    }
}
