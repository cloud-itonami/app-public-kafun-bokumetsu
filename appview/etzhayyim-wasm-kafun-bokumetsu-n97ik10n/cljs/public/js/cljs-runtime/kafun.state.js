goog.provide('kafun.state');
if((typeof kafun !== 'undefined') && (typeof kafun.state !== 'undefined') && (typeof kafun.state.app_meta !== 'undefined')){
} else {
kafun.state.app_meta = reagent.core.atom.cljs$core$IFn$_invoke$arity$1(cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"xrpc","xrpc",-1294004094),new cljs.core.Keyword(null,"relative-path","relative-path",1848635172),new cljs.core.Keyword(null,"xrpc-namespaces","xrpc-namespaces",-1634760538),new cljs.core.Keyword(null,"domains","domains",1410387719),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"nanoid","nanoid",-90964628),new cljs.core.Keyword(null,"title","title",636505583),new cljs.core.Keyword(null,"project","project",1124394579),new cljs.core.Keyword(null,"kind","kind",-717265803)],[true,"appview/etzhayyim-wasm-kafun-bokumetsu-n97ik10n/cljs/src/kafun/desktop.cljs",new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, ["com.etzhayyim.apps.kafun.agent","com.etzhayyim.apps.kafun.fund","com.etzhayyim.apps.kafun.cap","com.etzhayyim.apps.kafun.evolution"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, ["kafun-bokumetsu.etzhayyim.com","n97ik10n.etzhayyim.com"], null),"etzhayyim-wasm-kafun-bokumetsu-n97ik10n","n97ik10n","Kafun Bokumetsu N97ik10n","etzhayyim-project-public-kafun-bokumetsu","appview"]));
}
kafun.state.app_meta_value = (function kafun$state$app_meta_value(k){
return cljs.core.get.cljs$core$IFn$_invoke$arity$2(cljs.core.deref(kafun.state.app_meta),k);
});

//# sourceMappingURL=kafun.state.js.map
