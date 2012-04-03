package bgu.dcr.az.cpu.client.wgt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class AzButton extends Composite implements HasText, HasClickHandlers {

	@UiField
	SpanElement text;
	@UiField
	ButtonElement button;
	@UiField
	Style style;

	boolean pushButton = false;

	private static AzButtonUiBinder uiBinder = GWT
			.create(AzButtonUiBinder.class);

	interface AzButtonUiBinder extends UiBinder<Widget, AzButton> {
	}

	interface Style extends CssResource {
		String active();
	}

	public AzButton() {
		initWidget(uiBinder.createAndBindUi(this));
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pushButton) {
					setPushed(!isPushed());
				}
			}
		});
	}

	public void setPushed(boolean pushed){
		if (pushed) {
			button.addClassName(style.active());
		}else {
			button.removeClassName(style.active());
		}
	}
	
	public boolean isPushed(){
		return button.getClassName().contains(style.active());
	}
	
	public void setPushButton(boolean pushButton) {
		this.pushButton = pushButton;
	}

	public boolean isPushButton() {
		return pushButton;
	}
	
	@Override
	public String getText() {
		return text.getInnerText();
	}

	@Override
	public void setText(String t) {
		text.setInnerText(t);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

}