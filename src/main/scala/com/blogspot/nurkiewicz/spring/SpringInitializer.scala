package com.blogspot.nurkiewicz.spring

import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import javax.servlet.ServletContext

class SpringInitializer extends WebApplicationInitializer {

	def registerDispatcherServlet(container: ServletContext, dispatcherContext: AnnotationConfigWebApplicationContext) {
		val dispatcher = container.addServlet("mvc", new DispatcherServlet(dispatcherContext))
		dispatcher.setLoadOnStartup(1)
		dispatcher.addMapping("*.htm")
	}

	def createDispatcherContext() = {
		val dispatcherContext = new AnnotationConfigWebApplicationContext
		dispatcherContext.register(classOf[MvcConfiguration])
		dispatcherContext
	}

	def createRootContext(container: ServletContext) {
		val rootContext = new AnnotationConfigWebApplicationContext
		rootContext.register(classOf[SpringConfiguration])
		container.addListener(new ContextLoaderListener(rootContext))
	}

	def onStartup(container: ServletContext) {
		createRootContext(container)
		registerDispatcherServlet(container, createDispatcherContext())
	}

}