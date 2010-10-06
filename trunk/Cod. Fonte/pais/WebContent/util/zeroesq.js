function completaZerosEsquerda(numero, tamanho) {
    var ret = "";
    if(numero.length > 0){
        var qtdCompleta = tamanho - numero.length; 
        var zeros = "";
	    for (var i = 0; i < qtdCompleta; i++) {
	        zeros += "0";
	    }
        ret = zeros + numero;
    }
    return ret;
        
}

