dados = "";
dadosPGTO = "";
dadosPorcentagem = "";
dadosGerais = "";


$(function() {
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
	 dados = $('#graficoContratos').attr('data-valor'); //d.data('teste')
	dadosPGTO = $('#graficoPagamento').attr('data-pg');
	teste = $('#graficoPagamento').attr('data-teste');
	console.log(teste);
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







$(function() {
	var graficoLancamentosValor = new contratos.GraficoValorTotal();
	graficoLancamentosValor.iniciar();
	var graficoPGTO = new contratos.GraficoPagamento();
	graficoPGTO.iniciar();
	
});
	

