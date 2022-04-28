package io.github.nabil.ptolemy.rcp.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import io.github.nabil.ptolemy.rcp.model.Account;

public class AccountFilter extends ViewerFilter {

	private String searchString;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}

		String sk = searchString.toLowerCase().trim();

		try {
			Account p = (Account) element;
			if (p.getName() != null && p.getName().toLowerCase().matches(sk)) {
				return true;
			}
			if (p.getDescription() != null && p.getDescription().toLowerCase().matches(sk)) {
				return true;
			}
			if (p.getUserid() != null && p.getUserid().toLowerCase().matches(sk)) {
				return true;
			}
			if (p.getNotes() != null && p.getNotes().toLowerCase().matches(sk)) {
				return true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return false;
	}

	public void setSearchText(String s) {
		// ensure that the value can be used for matching
		this.searchString = ".*" + s + ".*";
	}
}
