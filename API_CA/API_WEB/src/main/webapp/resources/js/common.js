/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

function generateSize(pageSize)
{	
	var size=100;
	if (pageSize==''){
		return size;
	}else{
		size=pageSize;
	}		
	//console.log("size: "+size)
	return size;
}

function generateOffset(page, pageSize)
{
	if (page=='')
	{
		page=1;
	}
	if (pageSize=='')
	{
		pageSize=100;
	}
	//console.log("page: "+page+" pageSize: "+pageSize)
	var n=(page-1)*pageSize
	//console.log("offset: ")
	//console.log(n)
	return n;
}

function generateOrder(orden,names,positions)
{	
	var orderArray=new Array();
	
	var p1="";
	var p2="";
	
	if (orden.indexOf("-")==0)
	{
		p2="desc";
	}
	else		
	{
		p2="asc";
	}
	
	p1=orden;
	if (p1.indexOf("-")==0||p1.indexOf("+")==0)
	{
		if (p1.length>1)
		{
			p1=p1.substring(1,p1.length);
		}
	}
	
	for (var i=0;i<names.length;i++)
	{

		var name=names[i];
		
		if (p1.toLowerCase()==name.toLowerCase())
		{			
			p1=positions[i];
			break;
		}
	}	
	
	
	if (p1!=orden)
	{
		orderArray.push(p1);
		orderArray.push(p2);
	}
	

	//console.log(orderArray)
	return orderArray;
}


function genAttrInfo(fieldName, fieldValue)
{
	var linkableValue="";		
	if ((fieldValue+"").indexOf("http")==0)
	{
		if ((fieldName+"").indexOf("imagen")==0){
			
			linkableValue="<p align='right'><img src='"+fieldValue+"' alt='imagen' /></p> "
			
		}else{
			linkableValue="<a href='"+fieldValue+"' target='external"+fieldName+"'id='static"+fieldName+"'>"+fieldValue+"</a>"
		}
			
		
	}
		
	var dateValue="";	
	//console.log(fieldValue)
	//console.log(isDate(fieldValue))
	if (isDate(fieldValue))
	{
		//console.log(fieldValue)
		theDate=Date.parse(fieldValue);
		if ((fieldValue.indexOf('T00:00:00')>0)||(fieldValue.indexOf('T')<0))
		{
			dateValue=theDate.toString('dd-MM-yyyy')
		}else{
			dateValue=theDate.toString('dd-MM-yyyy HH:mm:ss')
		}
		//console.log(dateValue)
		dateValue=formatearFechaHora(fieldValue);
	}
	var numberValue="";	
	if (typeof fieldValue === 'number')
	{
		
		if (fieldName.indexOf("ETRS89")>=0)
		{
			numberValue=formatETRS(fieldValue);
		}
		else
		{		
			if ($.inArray( fieldName, moneyFields )>=0)
			{
				numberValue=formatMoney(fieldValue);		
			}else{
				numberValue=formatNumber(fieldValue);				
			}
		}		
	}
	var booleanValue="";	
	if (typeof fieldValue === 'boolean')
	{
		booleanValue = translateBoolean(fieldValue)		
	}
	var objectValue="";	
	if (typeof fieldValue === 'object')
	{
		objectValue = JSON.stringify(fieldValue)		
	}
	
	
	
	
	var htmlGen="";
	htmlGen+="<div class='form-group row'>";
	htmlGen+="<p class='col-sm-2 text-capitalize'>"+fieldName+"</p>";
	htmlGen+="<div id='fila_"+fieldName+"' class='col-sm-10'>";
	if ((linkableValue=='')&&(dateValue=='')&&(numberValue=='')&&(booleanValue=='')&&(objectValue==''))
	{
		htmlGen+="<p id='static"+fieldName+"'>"+fieldValue+"</p>";
	}
	else if (linkableValue!='')
	{
		htmlGen+=linkableValue;
	}else if (dateValue!='')
	{
		htmlGen+="<p id='static"+fieldName+"'>"+dateValue+"</p>";
	}else if (numberValue!='')
	{		
		htmlGen+="<p id='static"+fieldName+"'>"+ numberValue+"</p>";
	}
	else if (booleanValue!='')
	{		
		htmlGen+="<p id='static"+fieldName+"'>"+ booleanValue+"</p>";
	}
	else if (objectValue!='')
	{		
		htmlGen+="<p id='static"+fieldName+"'>"+ objectValue+"</p>";
	}
	
	
	htmlGen+="</div>";
	htmlGen+="</div>";	
	
	$("#formulario").append(htmlGen);
}


function translateBoolean (fieldValue)
{
	booleanValue = "No";
	if (fieldValue==true){
		booleanValue = "Si"
	}
	return booleanValue;
}

function isDate (value) {
	if (value.length<10)
	{
		return false;
	}
	var localDate = Date.parse(value);
	return (localDate!=null)
}

function formatNumber(value)
{
	var number = numeral(value);
	return number.format('0,0.[0000]');
}

function formatETRS(value)
{	
	if (value!=null)
	{
		if (value!=0)
		{
			var stringValue=value+"";
			return stringValue.replace(".",",");	
		}
	}
	return "";
}

function formatMoney(value)
{
	
	return formatNumber(value)+(' \u20AC');
}

function formatearFechaHora(fieldValue){
	theDate=Date.parse(fieldValue);
	var dateValue;
	if ((fieldValue.indexOf('T00:00:00')>0)||(fieldValue.indexOf('T')<0))
	{
		dateValue=theDate.toString('dd-MM-yyyy')
	}else{
		dateValue=theDate.toString('dd-MM-yyyy HH:mm:ss')
	}
	return dateValue;
}

function formatearHora(fieldValue){
	theDate=Date.parse(fieldValue);
	var dateValue;
	
	dateValue=theDate.toString('HH:mm:ss')
	
	return dateValue;
}


//load a locale
numeral.register('locale', 'es', {
    delimiters: {
        thousands: '.',
        decimal: ','
    },
    currency: {
        symbol: '€'
    }
});

// switch between locales
numeral.locale('es');