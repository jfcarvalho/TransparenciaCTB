dados = "";
dadosPGTO = "";
dadosPorcentagem = "";
dadosGerais = "";
var empresas = new Array();
var valores = new Array();



$(function() {
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
	teste = $('#graficoEmpresas').attr('data-empresas');
	dados = $('#graficoContratos').attr('data-valor'); //d.data('teste')
	dadosPGTO = $('#graficoPagamento').attr('data-pg');
	
	//console.log(teste)
	primeiro = teste.split(",");
	var segundo = new Array();
	for(var i=0; i < primeiro.length; i++) {
		segundo[i] = primeiro[i].split("=");
	}
	segundo[0][0] = segundo[0][0].replace("{", "");
	//segundo[segundo.length][segundo.length] = segundo[segundo.length][segundo.length].replace("}", "");
	segundol = segundo.length;
	segundoll = segundo[segundol-1].length
	segundo[segundol-1][segundoll-1] = segundo[segundol-1][segundoll-1].replace("}", "")
	for(var j=0; j < segundo.length; j++)
		{
			empresas[j] = segundo[j][0];
			valores[j] = segundo[j][1];
		}
	console.log(empresas);
	console.log(valores)
});

var contratos = contratos || {};

contratos.GraficoValorTotal = (function() {
	function GraficoValorTotal() {
		this.ctx = $('#graficoValorTotal')[0].getContext('2d');
}	

GraficoValorTotal.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosAno = new Chart(this.ctx, {
		type: 'line',
		data: {
			labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			datasets: [{
				label: 'Valores de lançamentos por mês',
	    		backgroundColor: "rgba(26,179,148,0.5)",
                pointBorderColor: "rgba(26,179,148,1)",
                pointBackgroundColor: "#fff",
                data: dados.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoValorTotal;

}());


contratos.GraficoPagamento = (function() {
	function GraficoPagamento() {
		this.ctx = $('#graficoDePagamentos')[0].getContext('2d');
}	

GraficoPagamento.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoPGTO = new Chart(this.ctx, {
		type: 'pie',
		data: {
			labels: ['Lancamentos pagos', 'Lançamentos não pagos'],
			datasets: [{
				backgroundColor: ["#FF6384", "#36A2EB"],
                hoverBackgroundColor: ["#FF6384", "#36A2EB"],
				data: dadosPGTO.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoPagamento;

}());


contratos.GraficoValorEmpresas = (function() {
	function GraficoValorEmpresas() {
		this.ctx = $('#graficoValorEmpresas')[0].getContext('2d');
}	

GraficoValorEmpresas.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosEmpresas = new Chart(this.ctx, {
		type: 'bar',
		data: {
			labels: empresas,
			datasets: [{
				label: 'Valores de lançamentos por mês',
				backgroundColor: "rgba(235,1,1,0.4)",
                pointBorderColor: "rgba(26,179,148,1)",
                pointBackgroundColor: "#fff",
                data: valores
			}]
		},
		
		
	});
}

return GraficoValorEmpresas;

}());




$(function() {
	var GraficoEmpresas = new contratos.GraficoValorEmpresas();
	GraficoEmpresas.iniciar();
	var graficoLancamentosValor = new contratos.GraficoValorTotal();
	graficoLancamentosValor.iniciar();
	var graficoPGTO = new contratos.GraficoPagamento();
	graficoPGTO.iniciar();
	
	
});
	

