goog.provide('kafun.ui');
kafun.ui.chrome_section = (function kafun$ui$chrome_section(label,body){
return new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"section","section",-300141526),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"panel"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h2","h2",-372662728),label], null),body], null);
});
kafun.ui.chips_list = (function kafun$ui$chips_list(items){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ul","ul",-1349521403),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"chips"], null),(function (){var iter__5480__auto__ = (function kafun$ui$chips_list_$_iter__22396(s__22398){
return (new cljs.core.LazySeq(null,(function (){
var s__22398__$1 = s__22398;
while(true){
var temp__5823__auto__ = cljs.core.seq(s__22398__$1);
if(temp__5823__auto__){
var s__22398__$2 = temp__5823__auto__;
if(cljs.core.chunked_seq_QMARK_(s__22398__$2)){
var c__5478__auto__ = cljs.core.chunk_first(s__22398__$2);
var size__5479__auto__ = cljs.core.count(c__5478__auto__);
var b__22400 = cljs.core.chunk_buffer(size__5479__auto__);
if((function (){var i__22399 = (0);
while(true){
if((i__22399 < size__5479__auto__)){
var i = cljs.core._nth(c__5478__auto__,i__22399);
cljs.core.chunk_append(b__22400,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"li","li",723558921),i], null));

var G__22473 = (i__22399 + (1));
i__22399 = G__22473;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__22400),kafun$ui$chips_list_$_iter__22396(cljs.core.chunk_rest(s__22398__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__22400),null);
}
} else {
var i = cljs.core.first(s__22398__$2);
return cljs.core.cons(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"li","li",723558921),i], null),kafun$ui$chips_list_$_iter__22396(cljs.core.rest(s__22398__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5480__auto__(items);
})()], null);
});
kafun.ui.facts_grid = (function kafun$ui$facts_grid(){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"section","section",-300141526),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"facts"], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),"Project"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"strong","strong",269529000),new cljs.core.Keyword(null,"project","project",1124394579).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))], null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),"Nanoid"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"strong","strong",269529000),new cljs.core.Keyword(null,"nanoid","nanoid",-90964628).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))], null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),"Domains"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"strong","strong",269529000),cljs.core.count(new cljs.core.Keyword(null,"domains","domains",1410387719).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta)))], null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div","div",1057191632),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),"XRPC"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"strong","strong",269529000),(cljs.core.truth_(new cljs.core.Keyword(null,"xrpc","xrpc",-1294004094).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta)))?"enabled":"not configured")], null)], null)], null);
});
kafun.ui.domains_panel = (function kafun$ui$domains_panel(){
return kafun.ui.chrome_section("Domains",((cljs.core.seq(new cljs.core.Keyword(null,"domains","domains",1410387719).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))))?new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"ul","ul",-1349521403),(function (){var iter__5480__auto__ = (function kafun$ui$domains_panel_$_iter__22434(s__22435){
return (new cljs.core.LazySeq(null,(function (){
var s__22435__$1 = s__22435;
while(true){
var temp__5823__auto__ = cljs.core.seq(s__22435__$1);
if(temp__5823__auto__){
var s__22435__$2 = temp__5823__auto__;
if(cljs.core.chunked_seq_QMARK_(s__22435__$2)){
var c__5478__auto__ = cljs.core.chunk_first(s__22435__$2);
var size__5479__auto__ = cljs.core.count(c__5478__auto__);
var b__22437 = cljs.core.chunk_buffer(size__5479__auto__);
if((function (){var i__22436 = (0);
while(true){
if((i__22436 < size__5479__auto__)){
var d = cljs.core._nth(c__5478__auto__,i__22436);
cljs.core.chunk_append(b__22437,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"li","li",723558921),d], null));

var G__22480 = (i__22436 + (1));
i__22436 = G__22480;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons(cljs.core.chunk(b__22437),kafun$ui$domains_panel_$_iter__22434(cljs.core.chunk_rest(s__22435__$2)));
} else {
return cljs.core.chunk_cons(cljs.core.chunk(b__22437),null);
}
} else {
var d = cljs.core.first(s__22435__$2);
return cljs.core.cons(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"li","li",723558921),d], null),kafun$ui$domains_panel_$_iter__22434(cljs.core.rest(s__22435__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__5480__auto__(new cljs.core.Keyword(null,"domains","domains",1410387719).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta)));
})()], null):new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"p","p",151049309),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"muted"], null),"No domain is declared for this app surface."], null)));
});
kafun.ui.xrpc_panel = (function kafun$ui$xrpc_panel(){
return kafun.ui.chrome_section("XRPC Surface",((cljs.core.seq(new cljs.core.Keyword(null,"xrpc-namespaces","xrpc-namespaces",-1634760538).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))))?kafun.ui.chips_list(new cljs.core.Keyword(null,"xrpc-namespaces","xrpc-namespaces",-1634760538).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))):new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"p","p",151049309),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"muted"], null),"No XRPC namespace is declared next to this app surface."], null)));
});
kafun.ui.source_panel = (function kafun$ui$source_panel(){
return kafun.ui.chrome_section("Source",new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"p","p",151049309),new cljs.core.Keyword(null,"relative-path","relative-path",1848635172).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))], null));
});
kafun.ui.top_section = (function kafun$ui$top_section(){
return new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"section","section",-300141526),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"top"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"p","p",151049309),"Cloudflare appview"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h1","h1",-1896887462),new cljs.core.Keyword(null,"title","title",636505583).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(cljs.core.deref(kafun.state.app_meta))], null)], null);
});
kafun.ui.root_view = (function kafun$ui$root_view(){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"main","main",-2117802661),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kafun.ui.top_section], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kafun.ui.facts_grid], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kafun.ui.domains_panel], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kafun.ui.xrpc_panel], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [kafun.ui.source_panel], null)], null);
});

//# sourceMappingURL=kafun.ui.js.map
