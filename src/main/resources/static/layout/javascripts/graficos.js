dados = "";

$('document').ready(function() 
		{
	 //var d = $(event.relatedTarget);
	 
	 dados = $('#grafico').attr('data-teste'); //d.data('teste');
})

var contratos = contratos || {};

contratos.GraficoLancamentos = (function() {
	function GraficoLancamentos() {
		this.ctx = $('#graficoLancamentos')[0].getContext('2d');
}	

GraficoLancamentos.prototype.iniciar = function() {
	//console.log('valor dos dados: ', dados.split(",").map(Number));
	var graficoLancamentosAno = new Chart(this.ctx, {
		type: 'line',
		data: {
			labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
			datasets: [{
				label: 'Lançamentos por mês',
	    		backgroundColor: "rgba(26,179,148,0.5)",
                pointBorderColor: "rgba(26,179,148,1)",
                pointBackgroundColor: "#fff",
                data: dados.split(",").map(Number)
			}]
		},
		
		
	});
}

return GraficoLancamentos;

}());

$(function() {
	var graficoLancamentos = new contratos.GraficoLancamentos();
	graficoLancamentos.iniciar();
});
	