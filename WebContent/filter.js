var req;
var sort;
var order;
var table;
var sign;
var price;

function init() {
    sort = document.getElementById("sort");
	order = document.getElementById("order");
	price = document.getElementById("price");
	sign = document.getElementById("sign");
	table = document.getElementById("table");
}

function doSort() {
        var url = "filtercomponent?orderBy=" + escape(order.value) + 
					"&sortBy=" + escape(sort.value) + 
					"&sign=" + escape(sign.value) + 
					"&price=" + escape(price.value);
        req = initRequest();
        req.open("GET", url, true);
        req.onreadystatechange = callback;
        req.send(null);
}

function initRequest() {
	var xmlreq;
	
	if (window.XMLHttpRequest) {

		// Создадим XMLHttpRequest объект для не-Microsoft браузеров
		return new XMLHttpRequest();

	} else if (window.ActiveXObject) {

		// Создадим XMLHttpRequest с помощью MS ActiveX
		try {
			// Попробуем создать XMLHttpRequest для поздних версий
			// Internet Explorer
			xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e1) {
			// Не удалось создать требуемый ActiveXObject
			try {
				// Пробуем вариант, который поддержат более старые версии
				//  Internet Explorer
				xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e2) {
				// Не в состоянии создать XMLHttpRequest с помощью ActiveX
			}
		}
	}
	return xmlreq;
}

function callback() {
    clearTable();
	
	if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages(req.responseXML);
        }
    }
}

function clearTable() {
    if (table.getElementsByTagName("tr").length > 0) {
        table.style.display = 'none';
        for (loop = table.childNodes.length -1; loop >= 0 ; loop--) {
            table.removeChild(table.childNodes[loop]);
        }
    }
}

function parseMessages(responseXML) {

    // no matches returned
    if (responseXML == null) {
        return false;
    } else {

        var components = responseXML.getElementsByTagName("components")[0];

        if (components.childNodes.length > 0) {
            table.setAttribute("border", "1");
			table.style.display = 'table';
			head();

            for (loop = 0; loop < components.childNodes.length; loop++) {
                var component = components.childNodes[loop];
                var title = component.getElementsByTagName("title")[0];
                var producer = component.getElementsByTagName("producer")[0];
                var weight = component.getElementsByTagName("weight")[0];
				var price = component.getElementsByTagName("price")[0];
				var id = component.getElementsByTagName("id")[0];
                appendComponent(title.childNodes[0].nodeValue,
                    producer.childNodes[0].nodeValue,
                    weight.childNodes[0].nodeValue,
					price.childNodes[0].nodeValue,
					id.childNodes[0].nodeValue);
            }
        }
    }
}

function appendComponent(title, producer, weight, price, id) {

    var row;
    var cellTitle;
	var cellProducer;
	var cellWeight;
	var cellPrice;
	var link;
	
    row = document.createElement("tr");
    table.appendChild(row);

    cellTitle = document.createElement("td");
	link = document.createElement("a");
	link.setAttribute("href", "showcomponent?id=" + id);
	link.appendChild(document.createTextNode(title));
	cellTitle.appendChild(link);
	row.appendChild(cellTitle);
	
	cellProducer = document.createElement("td");
	cellProducer.appendChild(document.createTextNode(producer));
	row.appendChild(cellProducer);
	
	cellWeight = document.createElement("td");
	cellWeight.appendChild(document.createTextNode(weight));
	row.appendChild(cellWeight);
	
	cellPrice = document.createElement("td");
	cellPrice.appendChild(document.createTextNode(price));
    row.appendChild(cellPrice);
}

function head() {
		trHead = document.createElement("tr");
		table.appendChild(trHead);
		
		tdTitle = document.createElement("td");
		tdTitle.appendChild(document.createTextNode("Title"));
		trHead.appendChild(tdTitle);
		
		tdProducer = document.createElement("td");
		tdProducer.appendChild(document.createTextNode("Producer"));
		trHead.appendChild(tdProducer);
		
		tdWeight = document.createElement("td");
		tdWeight.appendChild(document.createTextNode("Weight"));
		trHead.appendChild(tdWeight);
		
		tdPrice = document.createElement("td");
		tdPrice.appendChild(document.createTextNode("Price"));
		trHead.appendChild(tdPrice);
}

