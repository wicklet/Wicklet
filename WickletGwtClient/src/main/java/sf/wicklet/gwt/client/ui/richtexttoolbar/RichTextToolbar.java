/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sf.wicklet.gwt.client.ui.richtexttoolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class RichTextToolbar extends Composite {

	/**
	 * This {@link ClientBundle} is used for all the button icons. Using a bundle
	 * allows all of these images to be packed into a single image, which saves a
	 * lot of HTTP requests, drastically improving startup time.
	 */
	public interface Images extends ClientBundle {
		ImageResource bold();
		ImageResource createLink();
		ImageResource hr();
		ImageResource indent();
		ImageResource insertImage();
		ImageResource italic();
		ImageResource justifyCenter();
		ImageResource justifyLeft();
		ImageResource justifyRight();
		ImageResource ol();
		ImageResource outdent();
		ImageResource removeFormat();
		ImageResource removeLink();
		ImageResource strikeThrough();
		ImageResource subscript();
		ImageResource superscript();
		ImageResource ul();
		ImageResource underline();
		//
		ImageResource quit();
		ImageResource save();
		ImageResource revert();
		ImageResource txt();
	}

	/**
	 * This {@link Constants} interface is used to make the toolbar's strings
	 * internationalizable.
	 */
	public interface Strings extends Constants {
		String black();
		String blue();
		String bold();
		String color();
		String createLink();
		String font();
		String green();
		String hr();
		String indent();
		String insertImage();
		String italic();
		String justifyCenter();
		String justifyLeft();
		String justifyRight();
		String large();
		String medium();
		String normal();
		String ol();
		String outdent();
		String red();
		String removeFormat();
		String removeLink();
		String size();
		String small();
		String strikeThrough();
		String subscript();
		String superscript();
		String ul();
		String underline();
		String white();
		String xlarge();
		String xsmall();
		String xxlarge();
		String xxsmall();
		String yellow();
		//
		String quit();
		String save();
		String revert();
		String txt();
	}

	/**
	 * We use an inner EventHandler class to avoid exposing event methods on the
	 * RichTextToolbar itself.
	 */
	@SuppressWarnings("synthetic-access")
	private class EventHandler implements ClickHandler, ChangeHandler, KeyUpHandler {
		@Override
		public void onChange(final ChangeEvent event) {
			final Widget sender = (Widget)event.getSource();
			if (sender == backColors) {
				formatter.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
				backColors.setSelectedIndex(0);
			} else if (sender == foreColors) {
				formatter.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
				foreColors.setSelectedIndex(0);
			} else if (sender == fonts) {
				formatter.setFontName(fonts.getValue(fonts.getSelectedIndex()));
				fonts.setSelectedIndex(0);
			} else if (sender == fontSizes) {
				formatter.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
				fontSizes.setSelectedIndex(0);
		}}

		@Override
		public void onClick(final ClickEvent event) {
			final Widget sender = (Widget)event.getSource();
			if (toolbarHandler != null) {
				if (sender == quit) {
					toolbarHandler.quit();
					return;
				} else if (sender == save) {
					toolbarHandler.save();
					return;
				} else if (sender == revert) {
					toolbarHandler.revert();
					return;
				} else if (sender == txt) {
					toolbarHandler.editAsText(richText.getHTML());
					return;
			}}
			if (formatter != null) {
				if (sender == bold) {
					formatter.toggleBold();
				} else if (sender == italic) {
					formatter.toggleItalic();
				} else if (sender == underline) {
					formatter.toggleUnderline();
				} else if (sender == subscript) {
					formatter.toggleSubscript();
				} else if (sender == superscript) {
					formatter.toggleSuperscript();
				} else if (sender == strikethrough) {
					formatter.toggleStrikethrough();
				} else if (sender == indent) {
					formatter.rightIndent();
				} else if (sender == outdent) {
					formatter.leftIndent();
				} else if (sender == justifyLeft) {
					formatter.setJustification(RichTextArea.Justification.LEFT);
				} else if (sender == justifyCenter) {
					formatter.setJustification(RichTextArea.Justification.CENTER);
				} else if (sender == justifyRight) {
					formatter.setJustification(RichTextArea.Justification.RIGHT);
				} else if (sender == insertImage) {
					final String url = Window.prompt("Enter an image URL:", "http://");
					if (url != null) {
						formatter.insertImage(url);
				}} else if (sender == createLink) {
					final String url = Window.prompt("Enter a link URL:", "http://");
					if (url != null) {
						formatter.createLink(url);
				}} else if (sender == removeLink) {
					formatter.removeLink();
				} else if (sender == hr) {
					formatter.insertHorizontalRule();
				} else if (sender == ol) {
					formatter.insertOrderedList();
				} else if (sender == ul) {
					formatter.insertUnorderedList();
				} else if (sender == removeFormat) {
					formatter.removeFormat();
				} else if (sender == richText) {
					// We use the RichTextArea's onKeyUp event to update the toolbar status.
					// This will catch any cases where the user moves the cursur using the
					// keyboard, or uses one of the browser's built-in keyboard shortcuts.
					updateStatus();
		}}}

		//        protected void enableFormatter() {
		//            if (formatterButtons != null) {
		//                for ( final ButtonBase button: formatterButtons) {
		//                    button.setEnabled(true);
		//            }}
		//            formatter = richText.getFormatter();
		//        }
		//
		//        protected void disableFormatter() {
		//            if (formatterButtons != null) {
		//                for ( final ButtonBase button: formatterButtons) {
		//                    button.setEnabled(false);
		//            }}
		//            formatter = null;
		//        }

		@Override
		public void onKeyUp(final KeyUpEvent event) {
			final Widget sender = (Widget)event.getSource();
			if (sender == richText) {
				// We use the RichTextArea's onKeyUp event to update the toolbar status.
				// This will catch any cases where the user moves the cursur using the
				// keyboard, or uses one of the browser's built-in keyboard shortcuts.
				updateStatus();
		}}
	}

	private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
		RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
		RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
		RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
		RichTextArea.FontSize.XX_LARGE
	};

	private final Images images = (Images)GWT.create(Images.class);
	private final Strings strings = (Strings)GWT.create(Strings.class);
	@SuppressWarnings("synthetic-access")
	private final EventHandler handler = new EventHandler();

	private final RichTextArea richText;
	private final IToolbarHandler toolbarHandler;
	private final RichTextArea.Formatter formatter;

	private final VerticalPanel outer = new VerticalPanel();
	private final HorizontalPanel topPanel = new HorizontalPanel();
	private final HorizontalPanel bottomPanel = new HorizontalPanel();
	private ToggleButton bold;
	private ToggleButton italic;
	private ToggleButton underline;
	private ToggleButton subscript;
	private ToggleButton superscript;
	private ToggleButton strikethrough;
	private PushButton indent;
	private PushButton outdent;
	private PushButton justifyLeft;
	private PushButton justifyCenter;
	private PushButton justifyRight;
	private PushButton hr;
	private PushButton ol;
	private PushButton ul;
	private PushButton insertImage;
	private PushButton createLink;
	private PushButton removeLink;
	private PushButton removeFormat;
	private PushButton quit;
	private PushButton save;
	private PushButton revert;
	private PushButton txt;
	private ButtonBase[] formatterButtons;

	private ListBox backColors;
	private ListBox foreColors;
	private ListBox fonts;
	private ListBox fontSizes;

	/**
	 * Creates a new toolbar that drives the given rich text area.
	 *
	 * @param richText the rich text area to be controlled
	 */
	public RichTextToolbar(final RichTextArea richText, final IToolbarHandler h) {
		this.richText = richText;
		formatter = richText.getFormatter();
		toolbarHandler = h;
		outer.add(topPanel);
		outer.add(bottomPanel);
		topPanel.setWidth("100%");
		bottomPanel.setWidth("100%");
		initWidget(outer);
		setStyleName("gwt-RichTextToolbar");
		richText.addStyleName("hasRichTextToolbar");
		if (toolbarHandler != null) {
			topPanel.add(quit = createPushButton(images.quit(), strings.quit()));
			topPanel.add(save = createPushButton(images.save(), strings.save()));
			topPanel.add(revert = createPushButton(images.revert(), strings.revert()));
			topPanel.add(txt = createPushButton(images.txt(), strings.txt()));
		}
		if (formatter != null) {
			formatterButtons = new ButtonBase[] {
				bold = createToggleButton(images.bold(), strings.bold()),
				italic = createToggleButton(images.italic(), strings.italic()),
				underline = createToggleButton(images.underline(), strings.underline()),
				subscript = createToggleButton(images.subscript(), strings.subscript()),
				superscript = createToggleButton(images.superscript(), strings.superscript()),
				justifyLeft = createPushButton(images.justifyLeft(), strings.justifyLeft()),
				justifyCenter = createPushButton(images.justifyCenter(), strings.justifyCenter()),
				justifyRight = createPushButton(images.justifyRight(), strings.justifyRight()),
				strikethrough = createToggleButton(images.strikeThrough(), strings.strikeThrough()),
				indent = createPushButton(images.indent(), strings.indent()),
				outdent = createPushButton(images.outdent(), strings.outdent()),
				hr = createPushButton(images.hr(), strings.hr()),
				ol = createPushButton(images.ol(), strings.ol()),
				ul = createPushButton(images.ul(), strings.ul()),
				insertImage = createPushButton(images.insertImage(), strings.insertImage()),
				createLink = createPushButton(images.createLink(), strings.createLink()),
				removeLink = createPushButton(images.removeLink(), strings.removeLink()),
				removeFormat = createPushButton(images.removeFormat(), strings.removeFormat()),
			};
			for (final ButtonBase button: formatterButtons) {
				topPanel.add(button);
		}}
		if (formatter != null) {
			bottomPanel.add(backColors = createColorList("Background"));
			bottomPanel.add(foreColors = createColorList("Foreground"));
			bottomPanel.add(fonts = createFontList());
			bottomPanel.add(fontSizes = createFontSizes());
			// We only use these handlers for updating status, so don't hook them up
			// unless at least basic editing is supported.
			richText.addKeyUpHandler(handler);
			richText.addClickHandler(handler);
		}
	}

	private ListBox createColorList(final String caption) {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);
		lb.addItem(caption);
		lb.addItem(strings.white(), "white");
		lb.addItem(strings.black(), "black");
		lb.addItem(strings.red(), "red");
		lb.addItem(strings.green(), "green");
		lb.addItem(strings.yellow(), "yellow");
		lb.addItem(strings.blue(), "blue");
		return lb;
	}

	private ListBox createFontList() {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);
		lb.addItem(strings.font(), "");
		lb.addItem(strings.normal(), "");
		lb.addItem("Verdana", "Verdana");
		lb.addItem("Trebuchet", "Trebuchet");
		lb.addItem("Arial", "Arial");
		lb.addItem("Georgia", "Georgia");
		lb.addItem("Times New Roman", "Times New Roman");
		lb.addItem("Andale Mono", "Andale Mono");
		lb.addItem("Courier New", "Courier New");
		return lb;
	}

	private ListBox createFontSizes() {
		final ListBox lb = new ListBox();
		lb.addChangeHandler(handler);
		lb.setVisibleItemCount(1);
		lb.addItem(strings.size());
		lb.addItem(strings.xxsmall());
		lb.addItem(strings.xsmall());
		lb.addItem(strings.small());
		lb.addItem(strings.medium());
		lb.addItem(strings.large());
		lb.addItem(strings.xlarge());
		lb.addItem(strings.xxlarge());
		return lb;
	}

	private PushButton createPushButton(final ImageResource img, final String tip) {
		final PushButton pb = new PushButton(new Image(img));
		pb.addClickHandler(handler);
		pb.setTitle(tip);
		return pb;
	}

	private ToggleButton createToggleButton(final ImageResource img, final String tip) {
		final ToggleButton tb = new ToggleButton(new Image(img));
		tb.addClickHandler(handler);
		tb.setTitle(tip);
		return tb;
	}

	/**
	 * Updates the status of all the stateful buttons.
	 */
	private void updateStatus() {
		if (formatter != null) {
			bold.setDown(formatter.isBold());
			italic.setDown(formatter.isItalic());
			underline.setDown(formatter.isUnderlined());
			subscript.setDown(formatter.isSubscript());
			superscript.setDown(formatter.isSuperscript());
			strikethrough.setDown(formatter.isStrikethrough());
	}}

	public static interface IToolbarHandler {
		void quit();
		void save();
		void revert();
		void editAsText(String html);
	}
}
