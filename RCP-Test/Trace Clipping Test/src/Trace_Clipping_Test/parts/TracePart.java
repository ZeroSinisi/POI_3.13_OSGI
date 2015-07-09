 
package Trace_Clipping_Test.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class TracePart {

	private FormToolkit toolkit;
	private ScrolledForm form;
	private Composite container;

	@Inject
	public TracePart() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		parent.setLayout(new GridLayout(1, false));
		
		form = toolkit.createScrolledForm(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(form);
		form.setText("Trace Scrolled Form");
		form.setLayout(new GridLayout(1, false));

		// Creating the Section
		Section section = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Section 1 for demonstration"); //$NON-NLS-1$
		section.setDescription("This demonstrates the usage of section");

		// Container for all the widgets in the section
		container = toolkit.createComposite(section, SWT.WRAP);
		container.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);

		// The expandable composite
		final ExpandableComposite expand = toolkit.createExpandableComposite(
				container,
				ExpandableComposite.TREE_NODE | ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(expand);
		expand.setLayout(new GridLayout(1, false));
		expand.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				container.layout(true, true);
				form.reflow(true);
			}
		});

		// The composite holding the items within the expanded composite
		final Composite expandItems = toolkit.createComposite(expand, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(expandItems);
		expandItems.setLayout(new GridLayout(2, false));

		// Add Labels and FormText to the Expanded Composite Items
		for (int i = 0; i < 5; i++) {
			Label expandLabel =
					toolkit.createLabel(expandItems, "Label " + i, SWT.WRAP);
			GridDataFactory.fillDefaults().grab(true, true)
					.applyTo(expandLabel);
			FormText expandText = toolkit.createFormText(expandItems, true);
			expandText.setText("This is the form text for label number " + i,
					false, false);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(expandText);
		}

		// Set clients
		expand.setClient(expandItems);

		section.setClient(container);

		// Debug Listeners
		form.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				System.out.println("============================================"
						+ "\nForm size: " + form.getSize()
						+ "\nContainer size: " + container.getSize()
						+ "\nExpandable size: " + expand.getSize()
						+ "\nExpandItems Size: " + expandItems.getSize());
			}
		});
	}
	
	@Focus
	public void onFocus() {
		form.setFocus();
	}

	@PreDestroy
	public void preDestroy() {
		toolkit.dispose();
	}
	
	
	
}