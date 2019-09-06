(function ($) {
	$.widget("ui.combobox", {
		options: {
			maxOptionSize: 50,
			maxOptionText: "... type to filter"
		},
		_create: function () {

			var self = this,
				select = this.element.hide(),
				selected = select.children(":selected"),
				value = selected.val() ? selected.text() : "",
				wrapper = this.wrapper = $("<span>")
				.addClass("ui-combobox")
				.insertAfter(select);

			var wrapperParent = $(wrapper).parent();
			var ele = $(wrapper).prev('.comboselect');
			var placeHolderVal = $(ele).attr('placeholder');
			var input = this.input = $("<input>")
				.appendTo(wrapper)
				.val(value)
				.addClass("ui-state-default ui-combobox-input")
				.attr('placeholder', placeHolderVal)
				.click(function () {
					$(this).autocomplete("search", "");


				})
				.autocomplete({
					delay: 0,
					minLength: 0,
					appendTo: wrapperParent,
					source: function (request, response) {
						var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
						var select_el = select.get(0); // get dom element
						var rep = new Array(); // response array
						input.data("autocomplete").showMaxOption = false;


						// simple loop for the options
						for (var i = 0; i < select_el.length; i++) {
							var text = select_el.options[i].text;
							// add element to result array
							if (select_el.options[i].value && (!request.term || matcher.test(text))) {
								rep.push({
									label: text,
									//.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(request.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>"),
									value: text,
									option: select_el.options[i],
									number: i + 1
								});
							}
							if (rep.length == self.options.maxOptionSize) {
								input.data("autocomplete").showMaxOption = true;
								input.data("autocomplete").maxOptionText = self.options.maxOptionText;
								break;
							}
						}
						// send response
						response(rep);




					},
					/* add */
					select: function (event, ui) {
						ui.item.option.selected = true;
						self._trigger("selected", event, {
							item: ui.item.option
						});
						select.trigger("change");
					},
					/* add */
					change: function (event, ui) {
						if (!ui.item) {
							var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex($(this).val()) + "$", "i"),
								valid = false;
							select.children("option").each(function () {
								if ($(this).text().match(matcher)) {
									this.selected = valid = true;
									return false;
								}
							});
							if (!valid) {
								// remove invalid value, as it didn't match anything
								$(this).val("");
								select.val("");
								input.data("autocomplete").term = "";
								return false;
							}
						}
					}
				})
				.addClass("ui-widget ui-widget-content ui-corner-left uiDropDown");

			input.data("autocomplete")._renderItem = function (ul, item) {
				return $("<li></li>")
					.data("item.autocomplete", item)
					.append("<a>" + item.label + "</a>")
					.appendTo(ul);
			};

			$("<a>")
				.attr("tabIndex", -1)
				.attr("title", "Show All Items")
				.appendTo(wrapper)
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass("ui-corner-all")
				.addClass("ui-corner-right ui-combobox-toggle uiDropDownArrow")
				.click(function () {
					input.val('');
					input.empty();

					// close if already visible
					if (input.autocomplete("widget").is(":visible")) {
						input.autocomplete("close");

						return;
					}

					// work around a bug (likely same cause as #5265)
					$(this).blur();

					// pass empty string as value to search for, displaying all results
					input.autocomplete("search", "");
					input.focus();
				});

		},

		destroy: function () {
			this.wrapper.remove();
			this.element.show();
			$.Widget.prototype.destroy.call(this);
		},

		autocomplete: function (value) {
			this.element.val(value);
			this.input.val($(this.element).children("option").filter(":selected").text());
		}

	});
})(jQuery);

$.extend($.ui.autocomplete.prototype, {
	_renderMenu: function (ul, items) {
		var self = this;
		$.each(items, function (index, item) {
			if (index < items.length) self._renderItem(ul, item);
		});
		if (self.element.data("autocomplete").showMaxOption) {
			ul.append("<li class='ui-autocomplete-max-option'>" + self.element.data("autocomplete").maxOptionText + "</li>");
		}
	}
});