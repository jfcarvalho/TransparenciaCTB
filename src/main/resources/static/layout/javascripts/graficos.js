dados = "";
dadosSaldo = "";
dadosPorcentagem = "";
dadosGerais = "";

$('document').ready(function() 
		{
	 //var d = $(event.relatedTarget);
	 
	 dados = $('#graficoValor').attr('data-valor'); //d.data('teste');
	 dadosSaldo = $('#graficoSaldo').attr('data-saldo');
	 dadosPorcentagem = $('#graficoPorcentagem').attr('data-porcentagem');
	 dadosGerais = $('#graficoGeral').attr('data-geral');
})

var contratos = contratos || {};

contratos.GraficoLancamentosValor = (function() {
	function GraficoLancamentosValor() {
		this.ctx = $('#graficoLancamentosValor')[0].getContext('2d');
}	

GraficoLancamentosValor.prototype.iniciar = function() {
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

return GraficoLancamentosValor;

}());



contratos.GraficoLancamentosSaldo = (function() {
	function GraficoLancamentosSaldo() {
		this.ctx = $('#graficoLancamentosSaldo')[0].getContext('2d');
}	

GraficoLancamentosSaldo.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosAno = new Chart(this.ctx, {
		type: 'bar',
		data: {
			labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
			datasets: [{
				label: 'Saldos por mês',
	    		backgroundColor: "rgba(235,1,1,0.4)",
                pointBorderColor: "rgba(235,1,1,1)",
                pointBackgroundColor: "#fff",
                data: dadosSaldo.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoLancamentosSaldo;

}());


contratos.GraficoLancamentosPorcentagem = (function() {
	function GraficoLancamentosPorcentagem() {
		this.ctx = $('#graficoLancamentosPorcentagem')[0].getContext('2d');
}	

GraficoLancamentosPorcentagem.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosAno = new Chart(this.ctx, {
		type: 'pie',
		data: {
			labels: ['Concluido', 'A concluir'],
			datasets: [{
				label: 'Lançamentos por mês',
	    		//backgroundColor: "rgba(26,179,148,0.5)",
            //    pointBorderColor: "rgba(26,179,148,1)",
                backgroundColor: ["#FF6384", "#36A2EB"],
                data: dadosPorcentagem.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoLancamentosPorcentagem;

}());

contratos.GraficoLancamentosGeral = (function() {
	function GraficoLancamentosGeral() {
		this.ctx = $('#graficoLancamentosGeral')[0].getContext('2d');
}	

GraficoLancamentosGeral.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosAno = new Chart(this.ctx, {
		type: 'polarArea',
		data: {
			labels: ['Saldo', 'Valor', 'Aditivo'],
			datasets: [{
				label: 'Lançamentos por mês',
	    		//backgroundColor: "rgba(26,179,148,0.5)",
            //    pointBorderColor: "rgba(26,179,148,1)",
                backgroundColor: ["#FF6384", "#36A2EB", "#16B2EB"],
                data: dadosGerais.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoLancamentosGeral;

}());










$(function() {
	var graficoLancamentosValor = new contratos.GraficoLancamentosValor();
	var graficoLancamentosSaldo = new contratos.GraficoLancamentosSaldo();
	var graficoLancamentosPorcentagem= new contratos.GraficoLancamentosPorcentagem();
	var graficoLancamentosGeral= new contratos.GraficoLancamentosGeral();
	graficoLancamentosValor.iniciar();
	graficoLancamentosSaldo.iniciar();
	graficoLancamentosPorcentagem.iniciar();
	graficoLancamentosGeral.iniciar();
});
	